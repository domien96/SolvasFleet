package solvas.service.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.*;

@Component
public class AuditInterceptor extends EmptyInterceptor  implements BeanFactoryAware{


    private DaoContext daoContext;


    private BeanFactory beanFactory;

    private MapperContext mapperContext;

    private HashMap<Object,Revision> transactionRevisions = new HashMap<>();

    private ObjectMapper objectMapper;

    private User getAuthenticatedUser(){
        try {
            return daoContext.getUserDao().findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        // Make sure there is no infinite loop
        if (entity instanceof Revision) {
            return false;
        }


        //make revision
        Revision revision = new Revision();
        revision.setMethod(MethodType.UPDATE);


        // Connect revision with a entity
        transactionRevisions.put(entity,revision);
        return false; // We do not make changes
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        // Make sure there is no infinite loop
        if (entity instanceof Revision) {
            return false;
        }

        // Make revision
        Revision revision = new Revision();
        revision.setMethod(MethodType.INSERT);


        // Connect revision with a entity
        transactionRevisions.put(entity,revision);
        return false; // We do not make changes
    }


    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        // Make sure there is no infinite loop
        if (entity instanceof Revision) {
            return;
        }

        // Make revision
        Revision revision = new Revision();
        revision.setMethod(MethodType.DELETE);

        // Connect revision with a entity
        transactionRevisions.put(entity,revision);
    }

    private Revision getPayloadAndId(Object entity, Revision revision){
        Object about = entity;
        if (entity instanceof FleetSubscription) {
            about =  ((FleetSubscription) entity).getVehicle();
            revision.setMethod(MethodType.UPDATE); // Fleet subscriptions are updates to the vehicle
        }
        try {
            revision.setPayload(objectMapper.writeValueAsString(mapperContext.getMapperForClass(about.getClass()).convertToApiModel((Model) about)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // Can we even catch an exception at this point
        }
        revision.setEntity(((Model)entity).getId());
        return revision;

    }


    @Override
    public void postFlush(Iterator entities) {
        // Break dependency cycle
        if (daoContext==null) {
            daoContext= beanFactory.getBean(DaoContext.class);
        }
        if (mapperContext==null) {
            mapperContext = beanFactory.getBean(MapperContext.class);
        }
        if (objectMapper==null){
            objectMapper = beanFactory.getBean(ObjectMapper.class);
        }

        final LocalDateTime transactionDateTime =LocalDateTime.now();

        transactionRevisions.entrySet().forEach(objectRevisionEntry -> {
            // No dao operations can be made before flush
            // Set logged in user
            objectRevisionEntry.getValue().setUser(getAuthenticatedUser());
            // Set type of entity
            objectRevisionEntry.getValue().setEntityType(
                    EntityType.fromClass(objectRevisionEntry.getValue().getClass()));
            // Set current time
            objectRevisionEntry.getValue().setLogDate(transactionDateTime);

            getPayloadAndId(objectRevisionEntry.getKey(),objectRevisionEntry.getValue()); // Id cannot be set before flush
            daoContext.getRevisionDao().save(objectRevisionEntry.getValue()); // Create new revision
        });
        transactionRevisions.clear(); // Clean used map, as the same interceptor is used while running spring
    }


    // Cycle in the loading of hibernate/ spring beans, this method solves it
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;

    }
}
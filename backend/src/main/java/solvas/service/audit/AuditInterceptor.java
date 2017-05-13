package solvas.service.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;
import solvas.service.models.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Intercepts changes made to the database, and logs them with additional metadata, such as the current user.
 *
 * @author Steven
 */
@Component
public class AuditInterceptor extends EmptyInterceptor {

    private DaoContext daoContext;
    private MapperContext mapperContext;
    private HashMap<Object, Revision> transactionRevisions = new HashMap<>();
    private ObjectMapper objectMapper;

    /**
     * Gets the current authenticated user (from spring security context)
     * @return current authenticated user
     */
    private User getAuthenticatedUser() {
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
        transactionRevisions.put(entity, revision);
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
        if (((Model) entity).isArchived()){
            revision.setMethod(MethodType.ARCHIVE);
        } else {
            revision.setMethod(MethodType.INSERT);
        }



        // Connect revision with a entity
        transactionRevisions.put(entity, revision);
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
        transactionRevisions.put(entity, revision);
    }

    /**
     * Updates a revision with the entity id and the payload
     * @param entity entity to be audited
     * @param revision revision to be completed
     * @return revision given as parameter
     */
    private Revision getPayloadAndId(Object entity, Revision revision) {
        Object about = entity;
        if (entity instanceof FleetSubscription) {
            about = ((FleetSubscription) entity).getVehicle();
            revision.setMethod(MethodType.UPDATE); // Fleet subscriptions are updates to the vehicle
        }
        try {
            revision.setPayload(objectMapper.writeValueAsString(mapperContext.getMapperForClass(about.getClass()).convertToApiModel((Model) about)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // Can we even catch an exception at this point
        }
        revision.setEntity(((Model) about).getId());
        return revision;

    }

    @Override
    public void postFlush(Iterator entities) {

        final LocalDateTime transactionDateTime = LocalDateTime.now();

        transactionRevisions.forEach((key, value) -> {
            // No dao operations can be made before flush
            // Set logged in user
            value.setUser(getAuthenticatedUser());
            // Set type of entity
            value.setEntityType(EntityType.fromClass(key.getClass()));
            // Set current time
            value.setLogDate(transactionDateTime);

            getPayloadAndId(key, value); // Id cannot be set before flush
            daoContext.getRevisionDao().save(value); // Create new revision
        });
        transactionRevisions.clear(); // Clean used map, as the same interceptor is used while running spring
    }

    /**
     * This bean depends on other beans. For that reason we have to wait for the application to be completely
     * initialised. Spring will then call this method for us.
     *
     * We can't autowire this, as this causes Spring to crash because there are cyclic dependencies.
     *
     * @param event The event containing the new context.
     */
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        AutowireCapableBeanFactory factory = event.getApplicationContext().getAutowireCapableBeanFactory();
        daoContext = factory.getBean(DaoContext.class);
        mapperContext = factory.getBean(MapperContext.class);
        // We want a fresh bean, since we need it to behave differently.
        objectMapper = factory.createBean(ObjectMapper.class);
        objectMapper.addMixIn(ApiModel.class, IgnoreDataMixin.class);
    }
}
package solvas.service.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.RevisionDao;
import solvas.persistence.api.dao.UserDao;
import solvas.service.models.*;

@Component
public class AuditInterceptor extends EmptyInterceptor {

    @Autowired
    private RevisionDao revisionDao;

    @Autowired
    private UserDao userDao;

    private HashMap<Object,Revision> transactionRevisions = new HashMap<>();

    private static ObjectMapper objectMapper = new ObjectMapper();

    private User getAuthenticatedUser(){
        try {
            return userDao.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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

        // Generate payload
        ArrayNode list =objectMapper.createArrayNode();
        for(int i=0; i < currentState.length;i++) { //currentState, previousState, propertyNames, types: all have same indices
            if (!previousState[i].equals(currentState[i])){
                list.add(objectMapper.createObjectNode().put("field",propertyNames[i])
                        .putPOJO("old",previousState[i])
                        .putPOJO("new",currentState[i])
                );
            }
        }

        //make revision
        Revision revision = new Revision();
        revision.setLogDate(LocalDateTime.now());
        revision.setEntityType(EntityType.fromClass(entity.getClass()));
        revision.setMethod(MethodType.UPDATE);

        // Add payload to revision
        try {
            revision.setPayload(objectMapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // Can we even catch an exception at this point
        }

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

        // Generate payload
        ObjectNode obj =objectMapper.createObjectNode();
        for(int i=0; i < propertyNames.length;i++) { //state propertyNames, types: all have same indices
           obj.putPOJO(propertyNames[i],state[i]);
        }

        // Make revision
        Revision revision = new Revision();
        revision.setLogDate(LocalDateTime.now());
        revision.setEntityType(EntityType.fromClass(entity.getClass()));
        revision.setMethod(MethodType.INSERT);

        // Add payload to revision
        try {
            revision.setPayload(objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // Can we even catch an exception at this point
        }

        // Connect revision with a entity
        transactionRevisions.put(entity,revision);
        return false; // We do not make changes
    }

    @Override
    public void postFlush(Iterator entities) {
        transactionRevisions.entrySet().forEach(objectRevisionEntry -> {
            objectRevisionEntry.getValue().setUser(getAuthenticatedUser()); // User cannot be set before flush
            objectRevisionEntry.getValue().setEntity(((Model) objectRevisionEntry.getKey()).getId()); // Id cannot be set before flush
            revisionDao.save(objectRevisionEntry.getValue()); // Create new revision
        });
        transactionRevisions.clear(); // Clean used map, as the same interceptor is used while running spring
    }


    @Override
    public void beforeTransactionCompletion(Transaction tx) {

        super.beforeTransactionCompletion(tx);
    }
}
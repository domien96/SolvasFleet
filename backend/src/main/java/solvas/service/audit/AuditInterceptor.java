package solvas.service.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

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
        ArrayNode list =objectMapper.createArrayNode();
        for(int i=0; i < currentState.length;i++) { //currentState, previousState, propertyNames, types: all have same indices
            if (!previousState[i].equals(currentState[i])){
                list.add(objectMapper.createObjectNode().put("field",propertyNames[i])
                        .putPOJO("Old",previousState[i])
                        .putPOJO("New",currentState[i])
                );
            }
        }
        Revision revision = new Revision();
        revision.setLogDate(LocalDateTime.now());
        revision.setEntityType(EntityType.USER); // Todo make something more meaningfull
        revision.setMethod(MethodType.UPDATE);
        try {
            revision.setPayload(objectMapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // Can we even catch an exception at this point
        }

        transactionRevisions.put(entity,revision);
        return false; // We do not make changes
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());

        // Make sure there is no infinite loop
        if (entity instanceof Revision) {
            return false;
        }
        Revision revision = new Revision();
        revision.setEntity(((Model) entity).getId());
        revision.setLogDate(LocalDateTime.now());
        revision.setEntityType(EntityType.USER); // Todo make something more meaningfull
        revision.setMethod(MethodType.INSERT);

        transactionRevisions.put(entity,revision);

        return false;
    }

    @Override
    public void postFlush(Iterator entities) {
        transactionRevisions.entrySet().forEach(objectRevisionEntry -> {
            objectRevisionEntry.getValue().setUser(getAuthenticatedUser());
            objectRevisionEntry.getValue().setEntity(((Model) objectRevisionEntry.getKey()).getId());
            revisionDao.save(objectRevisionEntry.getValue());
        });
        transactionRevisions.clear();


    }


    @Override
    public void beforeTransactionCompletion(Transaction tx) {

        super.beforeTransactionCompletion(tx);
    }
}
//https://www.mkyong.com/hibernate/hibernate-interceptor-example-audit-log/
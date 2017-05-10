package solvas.service.models;

import org.hibernate.type.EnumType;

import java.time.LocalDateTime;

/**
 * Created by steve on 09/05/2017.
 */
public class Revision extends Model {
    private int entity;
    private String entityType;
    private LocalDateTime logDate;
    private User user;
    private MethodType method;
    private String payload;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public int getEntity() {
        return entity;
    }

    public void setEntity(int entity) {
        this.entity = entity;

    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public MethodType getMethod() {
        return method;
    }

    public void setMethod(MethodType method) {
        this.method = method;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
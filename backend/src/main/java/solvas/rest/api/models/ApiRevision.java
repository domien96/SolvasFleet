package solvas.rest.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import solvas.service.models.MethodType;
import solvas.service.models.User;

import java.time.LocalDateTime;

/**
 * Revision in the API layer
 */
public class ApiRevision extends ApiModel {
    private int entity;
    private String entityType;
    private LocalDateTime logDate;
    private int user;
    private String method;
    private String payload;


    public int getUser() {
        return user;
    }

    public void setUser(int user) {
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    @JsonIgnore
    @Override
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @JsonIgnore
    @Override
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }
}
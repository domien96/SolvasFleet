package solvas.service.models;


import java.time.LocalDateTime;

/**
 * Models a Revision
 *
 * @author Steven
 */
public class Revision extends Model {
    /**
     * Every mutable model is has a unique identifier (in the scope of his class).
     * This id is stored during auditing. Together with the the class of the model,
     *  a unique reference is stored.
     */
    private int entity;

    /**
     * This stores the class of a model of which a log is stored.
     *  Together with the entity id (model id), the model can be uniquely identified.
     */
    private String entityType;

    /**
     * When logging the time and date of the change are stored as well.
     */
    private LocalDateTime logDate;
    /**
     * When logging actions, the authenticated user is stored in the logs as well.
     * @see User
     */
    private User user;
    /**
     * To easier identify what the action was that caused the auditing mechanise
     *  to be activated is kept.
     *  @see MethodType
     */
    private MethodType method;
    /**
     * The exact object which was changes is kept in json representation.
     * The representation should be of a ApiModel as the mappers contain
     *  some reference logic.
     */
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
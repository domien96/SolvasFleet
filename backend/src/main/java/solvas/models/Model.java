package solvas.models;

import java.time.LocalDateTime;

/**
 * Abstract model. Contains some common attributes and metadata for the models.
 *
 * @author David
 * @author Steven
 * @author domien
 * @author Niko Strijbol
 */
public abstract class Model {

    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Get the unique identifier for this model. When making a new model, obtaining a an identifier should be
     * left to the persistence layer. Once a model has an ID, the ID is guaranteed to stay the same, as long as
     * the model exists. Persistence layers must honour this requirement and must not change an existing id.
     *
     * @return The identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Set this model's identifier. This value must be unique. The persistence layer may impose additional
     * requirements on this attribute; such as a prohibition to manually change it.
     *
     * @param id The unique identifier for this model.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get when this model was created. This attribute is considered metadata, and managing it is best left to
     * the persistence layer. Once a model has been created and this attribute has been set, it must not change.
     * Persistence layers must honour this requirement and not change an existing timestamp.
     *
     * A model is considered created when it has been persisted or otherwise saved.
     *
     * @return The time and date at which this model was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Set the time at which this model was created. The persistence layer may impose additional
     * requirements on this attribute; such as a prohibition to manually change it.
     *
     * @param createdAt The date and time at which this model was last updated.
     */
    @SuppressWarnings("unused")
    private void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Get the date and time at which this model was last updated. This attribute is considered metadata, and managing
     * it is best left to the persistence layer.
     *
     * This value must be updated if one of the attributes of this model (excluding those marked as transient or
     * described as transient in the model's contract) is changed. An attribute is considered changed if the change is
     * persisted or otherwise saved.
     *
     * This attribute may also be updated when persisting or otherwise saving the model.
     *
     * @return The date and time at which this model was last updated.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Set the time at which this model was last updated. The persistence layer may impose
     * additional requirements on this attribute; such as a prohibition to manually change it.
     *
     * @param updatedAt The time at which this model was last updated.
     */
    @SuppressWarnings("unused")
    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
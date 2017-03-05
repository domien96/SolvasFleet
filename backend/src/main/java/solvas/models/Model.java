package solvas.models;

import java.time.LocalDateTime;

/**
 * Abstract Model
 * @author David on 3/1/17
 * @author Steven
 * @author domien
 */
public abstract class Model {
    protected int id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

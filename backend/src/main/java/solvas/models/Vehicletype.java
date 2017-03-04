package solvas.models;

import java.sql.Timestamp;

/**
 * Created by domien on 4/03/2017.
 */
public class Vehicletype extends Model {
    private String name;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

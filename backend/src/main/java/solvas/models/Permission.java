package solvas.models;

import java.sql.Timestamp;

/**
 * Created by domien on 4/03/2017.
 */
public class Permission extends Model {
    private String name;

    private Timestamp created_at;
    private Timestamp updated_at;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}

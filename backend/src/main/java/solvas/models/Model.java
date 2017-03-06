package solvas.models;

import java.util.Date; //Date is the only not giving a ClassCastException
                        // TODO model timezone

/**
 * Abstract Model
 * @author David on 3/1/17
 * @author Steven
 * @author domien
 */
public abstract class Model {
    protected int id;
    protected Date createdAt;
    protected Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

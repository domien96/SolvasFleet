package solvas.service.models;

/**
 * Models a permission
 * @author domien on 4/03/2017.
 */
public class Permission extends Model {
    private String action;
    private String resource;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}

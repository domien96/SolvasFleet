package solvas.service.models;

/**
 * Models a permission
 * @author domien on 4/03/2017.
 */
public class Permission extends Model {
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

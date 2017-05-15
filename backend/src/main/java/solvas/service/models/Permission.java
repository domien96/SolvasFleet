package solvas.service.models;

/**
 * Models a single permission.
 * This is a single action.
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

package solvas.service.models;

/**
 * Models a single permission.
 * This represents a single action a user can do.
 * e.g. edit a vehicle, view all users
 * @author domien on 4/03/2017.
 */
public class Permission extends Model {

    /**
     * This is the name of this permission.
     */
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

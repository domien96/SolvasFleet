package solvas.rest.api.models;

import javax.validation.constraints.NotNull;

/**
 * Schema for a contract as defined in the Permission
 * @author Steven Bastiaens
 */
public class ApiPermission extends ApiModel {

    @NotNull
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

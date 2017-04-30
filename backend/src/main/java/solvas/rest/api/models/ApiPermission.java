package solvas.rest.api.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import solvas.rest.api.models.serializers.PermissionSerializer;

import javax.validation.constraints.NotNull;

/**
 * Schema for a contract as defined in the Permission
 * @author Steven Bastiaens
 */
@JsonSerialize(using = PermissionSerializer.class)
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

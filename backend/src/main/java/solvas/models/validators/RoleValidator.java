package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.models.Role;
import solvas.rest.api.models.ApiRole;

/**
 * Validate Roles
 * @author David Vandorpe
 */
@Component
public class RoleValidator extends AbstractValidator<ApiRole> {
    {
        require("function");
    }
    /**
     * Create a validator for roles
     */
    public RoleValidator() {
        super(ApiRole.class);
    }
}

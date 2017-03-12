package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.models.Role;

/**
 * Validate Roles
 * @author David Vandorpe
 */
@Component
public class RoleValidator extends AbstractValidator<Role> {
    {
        require("function");
    }
    /**
     * Create a validator for roles
     */
    public RoleValidator() {
        super(Role.class);
    }
}

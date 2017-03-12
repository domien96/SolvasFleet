package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.rest.api.models.ApiUser;

/**
 * Validate Users
 * @author David Vandorpe
 */
@Component
public class UserValidator extends AbstractValidator<ApiUser> {
    {
        require("firstName", "lastName", "email");
    }

    /**
     * Create a validator for users
     */
    protected UserValidator() {
        super(ApiUser.class);
    }
}
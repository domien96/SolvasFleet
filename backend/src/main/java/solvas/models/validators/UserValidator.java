package solvas.models.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import solvas.rest.api.models.ApiUser;

/**
 * Validate Users
 * @author David Vandorpe
 */
@Component
public class UserValidator extends AbstractValidator<ApiUser> {
    {
        require("firstName", "lastName", "email");

        addValidation((target, errors) -> {
            if(target.getId() == 0) { // New object
                ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
            }
        });
    }

    /**
     * Create a validator for users
     */
    protected UserValidator() {
        super(ApiUser.class);
    }
}
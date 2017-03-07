package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.models.User;

/**
 * Validate Users
 * @author David Vandorpe
 */
@Component
public class UserValidator extends AbstractValidator<User> {
    /**
     * Create a validator for users
     */
    protected UserValidator() {
        super(User.class);
    }
}
package solvas.rest.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiUser;
import solvas.rest.api.models.ApiVehicle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate that an emailis unique in the database.
 *
 * This will not validate if the email is correctly formatted, it will just check to see if it exists in the database.
 *
 * @author Niko Strijbol
 */
@Component
public class UniqueEmailForUserValidator extends DaoContextAwareConstraintValidator
        implements ConstraintValidator<UniqueEmail, ApiUser> {

    /**
     * Creates this validator
     * @param context The dao context (Autowired)
     */
    @Autowired
    public UniqueEmailForUserValidator (DaoContext context) {
        super(context);
    }

    @Override
    public void initialize(UniqueEmail annotation) { }

    @Override
    public boolean isValid(ApiUser user, ConstraintValidatorContext context) {

        // An empty email is valid. (for this validator at least)
        if (StringUtils.isEmpty(user.getEmail())) {
            return true;
        }

        boolean isValid = getDaoContext().getUserDao().getByEmail(user.getEmail())
                .map(found -> found.getId() == user.getId()) // It can be it's own email.
                .orElse(true); // The email does not exist.

        // This is a class constraint, but we want to report is a field error
        if (!isValid) {
            registerFieldError("email", context);
        }
        return isValid;
    }
}
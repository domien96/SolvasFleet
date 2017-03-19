package solvas.models.validators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator to validate phone numbers. This validation uses Google's libphone validation library to validate the
 * phone numbers correctly for lots of countries.
 *
 * It is the validator for {@link PhoneNumber}.
 *
 * @see <a href="https://github.com/googlei18n/libphonenumber">libphone library</a>
 *
 * @author Niko Strijbol
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private PhoneNumberUtil phoneNumberUtil;
    private String countryCode;

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        this.phoneNumberUtil = PhoneNumberUtil.getInstance();
        this.countryCode = constraintAnnotation.countryCode();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // The Java Bean Validation specification recommends allowing null values.
        // You should check using @NotNull for nullness.
        if (value == null) {
            return true;
        }

        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(value, countryCode);
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
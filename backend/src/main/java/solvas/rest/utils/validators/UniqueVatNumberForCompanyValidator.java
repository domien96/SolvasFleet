package solvas.rest.utils.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Ensure vat number is unique for each company
 */
public class UniqueVatNumberForCompanyValidator extends DaoContextAwareConstraintValidator
        implements ConstraintValidator<UniqueVatNumber, String> {

    @Override
    public void initialize(UniqueVatNumber annotation) { }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || ! getDaoContext().getCompanyDao().findByVatNumber(value).isPresent();
    }
}
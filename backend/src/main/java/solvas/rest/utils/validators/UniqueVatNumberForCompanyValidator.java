package solvas.rest.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Ensure vat number is unique for each company
 */
@Component
public class UniqueVatNumberForCompanyValidator extends DaoContextAwareConstraintValidator
        implements ConstraintValidator<UniqueVatNumber, String> {

    @Autowired
    public UniqueVatNumberForCompanyValidator(DaoContext context) {
        super(context);
    }

    @Override
    public void initialize(UniqueVatNumber annotation) { }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || ! getDaoContext().getCompanyDao().findByVatNumber(value).isPresent();
    }
}
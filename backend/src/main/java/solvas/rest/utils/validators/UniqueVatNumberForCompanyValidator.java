package solvas.rest.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiCompany;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Ensure vat number is unique for each company
 */
@Component
public class UniqueVatNumberForCompanyValidator extends DaoContextAwareConstraintValidator
        implements ConstraintValidator<UniqueVatNumber, ApiCompany> {

    /**
     * Creates this validator
     * @param context The dao context (Autowired)
     */
    @Autowired
    public UniqueVatNumberForCompanyValidator(DaoContext context) {
        super(context);
    }

    @Override
    public void initialize(UniqueVatNumber annotation) { }

    @Override
    public boolean isValid(ApiCompany company, ConstraintValidatorContext context) {

        // If the VAT is null or empty, another annotation handles it.
        if (StringUtils.isEmpty(company.getVatNumber())) {
            return true;
        }

        boolean isValid = getDaoContext().getCompanyDao().findByVatNumber(company.getVatNumber())
                .map(found -> found.getId() == company.getId()) // It can be it's own number.
                .orElse(true); // The VAT does not exist.

        // This is a class constraint, but we want to report is a field error
        if (!isValid) {
            registerFieldError("vatNumber", context);
        }
        return isValid;
    }
}
package solvas.models.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Checks that a company exists.
 *
 * @author Niko Strijbol
 */
@Transactional(readOnly = true)
public class CompanyExistsValidator implements ConstraintValidator<CompanyExists, Integer> {

    private final CompanyDao companyDao;

    /**
     * Constructor.
     *
     * @param companyDao The dao to use.
     */
    @Autowired
    public CompanyExistsValidator(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public void initialize(CompanyExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        try {
            companyDao.find(value);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
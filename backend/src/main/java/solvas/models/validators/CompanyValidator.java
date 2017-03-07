package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.models.Company;

/**
 * Validate Companies
 *
 * @author David Vandorpe.
 */
@Component
public class CompanyValidator extends AbstractValidator<Company> {
    public CompanyValidator() {
        super(Company.class);
        require("name", "vatNumber", "phoneNumber", "address", "url");
    }
}

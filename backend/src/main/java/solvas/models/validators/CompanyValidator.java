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
    {
        require("name", "vatNumber", "phoneNumber", "address", "url");
        // Examples:
         /* addValidations((target, errors) -> {
             Do stuff
         });*/
    }
    /**
     * Create a validator for companies
     */
    public CompanyValidator() {
        super(Company.class);
    }
}

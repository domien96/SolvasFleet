package solvas.models.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiModel;

/**
 * Validate Companies
 *
 * @author David Vandorpe.
 */
@Component
public class CompanyValidator extends AbstractValidator<ApiCompany> {
    {
        require("name", "vatNumber", "phoneNumber", "address","address.city","address.country","address.houseNumber","address.postalCode","address.street");
        // Examples:target.getAddress()
         /* addValidations((target, errors) -> {
             Do stuff
         });*/
    }
    /**
     * Create a validator for companies
     */
    public CompanyValidator() {
        super(ApiCompany.class);
    }
}

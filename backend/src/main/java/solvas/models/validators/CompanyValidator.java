package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.rest.api.models.ApiModel;

/**
 * Validate Companies
 *
 * @author David Vandorpe.
 */
@Component
public class CompanyValidator extends AbstractValidator<ApiModel> {
    {
        require("name", "vatNumber", "phoneNumber", "address");
        // Examples:
         /* addValidations((target, errors) -> {
             Do stuff
         });*/
    }
    /**
     * Create a validator for companies
     */
    public CompanyValidator() {
        super(ApiModel.class);
    }
}

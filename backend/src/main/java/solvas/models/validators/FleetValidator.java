package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.rest.api.models.ApiFleet;

/**
 * @author Niko Strijbol
 */
@Component
public class FleetValidator extends AbstractValidator<ApiFleet> {
    {
        require("company");
    }

    public FleetValidator() {
        super(ApiFleet.class);
    }
}
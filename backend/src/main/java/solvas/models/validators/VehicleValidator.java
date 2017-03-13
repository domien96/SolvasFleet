package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.rest.api.models.ApiVehicle;

/**
 * Validate Vehicles
 * @author David Vandorpe
 */
@Component
public class VehicleValidator extends AbstractValidator<ApiVehicle> {
    {
        require("vin", "model", "type", "mileage", "year", "fleet");
    }
    /**
     * Create a validator for Vehicles
     */
    protected VehicleValidator() {
        super(ApiVehicle.class);
    }
}

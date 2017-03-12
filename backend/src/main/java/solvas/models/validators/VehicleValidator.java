package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.models.Vehicle;
import solvas.rest.api.models.ApiVehicle;

/**
 * Validate Vehicles
 * @author David Vandorpe
 */
@Component
public class VehicleValidator extends AbstractValidator<ApiVehicle> {
    {
        require("licensePlate", "chassisNumber", "model", "type", "mileage", "year", "company");
    }
    /**
     * Create a validator for Vehicles
     */
    protected VehicleValidator() {
        super(ApiVehicle.class);
    }
}

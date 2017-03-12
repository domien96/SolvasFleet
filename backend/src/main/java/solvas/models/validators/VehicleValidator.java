package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.models.Vehicle;

/**
 * Validate Vehicles
 * @author David Vandorpe
 */
@Component
public class VehicleValidator extends AbstractValidator<Vehicle> {
    {
        require("licensePlate", "chassisNumber", "model", "vehicleType", "kilometerCounter", "year", "company");
    }
    /**
     * Create a validator for Vehicles
     */
    protected VehicleValidator() {
        super(Vehicle.class);
    }
}

package solvas.models.validators;

import org.springframework.stereotype.Component;
import solvas.models.Vehicle;

/**
 * Validate Vehicles
 * @author David Vandorpe
 */
@Component
public class VehicleValidator extends AbstractValidator<Vehicle> {
    /**
     * Create a validator for Vehicles
     */
    protected VehicleValidator() {
        super(Vehicle.class);
    }
}

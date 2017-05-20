package solvas.rest.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiVehicle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate that a VIN is unique in the database.
 *
 * This will not validate if the VIN is correctly formatted, it will just check to see if it exists in the database.
 *
 * @author Niko Strijbol
 */
@Component
public class UniqueVinForVehicleValidator extends DaoContextAwareConstraintValidator
        implements ConstraintValidator<UniqueVin, ApiVehicle> {

    /**
     * Creates this validator
     * @param context The dao context (Autowired)
     */
    @Autowired
    public UniqueVinForVehicleValidator(DaoContext context) {
        super(context);
    }

    @Override
    public void initialize(UniqueVin annotation) { }

    @Override
    public boolean isValid(ApiVehicle vehicle, ConstraintValidatorContext context) {

        // A empty VIN is valid.
        if (StringUtils.isEmpty(vehicle.getVin())) {
            return true;
        }

        boolean isValid = getDaoContext().getVehicleDao().findByChassisNumber(vehicle.getVin())
                .map(found -> found.getId() == vehicle.getId()) // It can be it's own vin.
                .orElse(true); // The VAT does not exist.

        // This is a class constraint, but we want to report is a field error
        if (!isValid) {
            registerFieldError("vin", context);
        }
        return isValid;
    }
}
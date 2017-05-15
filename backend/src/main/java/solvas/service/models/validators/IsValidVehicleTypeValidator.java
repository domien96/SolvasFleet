package solvas.service.models.validators;

import org.springframework.beans.factory.annotation.Autowired;
import solvas.persistence.api.dao.VehicleTypeDao;
import solvas.service.VehicleService;
import solvas.service.models.VehicleType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Validations if a string has a corresponding vehicle type object.
 *
 * @author  domien on 14/05/2017.
 */
public class IsValidVehicleTypeValidator implements ConstraintValidator<IsValidVehicleType, String> {

    @Autowired
    private VehicleService service;

    @Override
    public void initialize(IsValidVehicleType constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return service.findAllVehicleTypes().contains(value);
    }
}

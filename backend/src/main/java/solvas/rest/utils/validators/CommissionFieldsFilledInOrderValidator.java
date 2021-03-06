package solvas.rest.utils.validators;

import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiCommission;
import solvas.rest.api.models.ApiUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that if a field is filled in, that every higher level field is also filled in.
 * This makes it impossible for the fleet field to be filled in, but not the company.
 * Created by domien on 20/05/2017.
 */
public class CommissionFieldsFilledInOrderValidator extends DaoContextAwareConstraintValidator
        implements ConstraintValidator<CommissionFieldsFilledInOrder, ApiCommission> {
    /**
     * Creates this validator
     *
     * @param context The dao context (Autowired)
     */
    public CommissionFieldsFilledInOrderValidator(DaoContext context) {
        super(context);
    }

    @Override
    public void initialize(CommissionFieldsFilledInOrder constraintAnnotation) {

    }

    @Override
    public boolean isValid(ApiCommission value, ConstraintValidatorContext context) {
        // we make an exception for vehicle id.
        // we start validating from vehicle type.
        return validateFromFleet(value,false);
    }

    /**
     * Checks whether the vehicle type has a valid value.
     * If vehicle type is filled in, all higher level fields must be filled in also.
     * @param value the commission
     * @param mustBeFilledIn holds track whether a previous field (on a lower level) is filled in.
     *                       If this is true, this means all the higher level fields must be filled in too.
     *                       If it is not true, it does not matter if the vehicle type is filled in or not.
     * @return is the commission is valid, for the level of the vehicle type field at least.
     */
    private boolean validateFromFleet(ApiCommission value, boolean mustBeFilledIn) {
        return !(mustBeFilledIn && value.getFleet()<=0) && validateFromCompany(value, value.getFleet() > 0);
    }

    private boolean validateFromCompany(ApiCommission value, boolean mustBeFilledIn) {
        return !(mustBeFilledIn && value.getCompany()<=0) && validateFromVehicleType(value, value.getCompany() > 0);
    }

    private boolean validateFromVehicleType(ApiCommission value, boolean mustBeFilledIn) {
        return !(mustBeFilledIn && value.getVehicleType()==null) && validateFromInsuranceType(value, value.getVehicleType() != null);
    }

    private boolean validateFromInsuranceType(ApiCommission value, boolean mustBeFilledIn) {
        return true; // this is the highest level
    }
}

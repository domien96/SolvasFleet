package solvas.rest.utils.validators;

import solvas.persistence.api.DaoContext;

import javax.validation.ConstraintValidatorContext;

/**
 * ConstraintValidator that can access the persistence layer
 */
public abstract class DaoContextAwareConstraintValidator {
    private DaoContext daoContext;

    public DaoContext getDaoContext() {
        return daoContext;
    }

    public void setDaoContext(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    /**
     * Creates this validator
     * @param context The dao context (Autowired)
     */
    public DaoContextAwareConstraintValidator(DaoContext context) { daoContext = context;}

    /**
     * Useful method to register a constraint validation as a field error. Calling this method will disable the
     * default handling by calling {@link ConstraintValidatorContext#disableDefaultConstraintViolation()}.
     *
     * @param field The field to register as.
     * @param context The context to register with.
     */
    protected void registerFieldError(String field, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}
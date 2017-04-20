package solvas.rest.utils.validators;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Build constraint validators, and pass the daoContext if needed
 */
@Component
public class DaoContextAwareConstraintValidatorFactory implements ConstraintValidatorFactory {
    private final DaoContext daoContext;

    /**
     * Create instance
     * @param daoContext DaoContext to pass to instances of {@link DaoContextAwareConstraintValidator}
     */
    @Autowired
    public DaoContextAwareConstraintValidatorFactory(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    @Override
    public final <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        T constraintValidator = run( NewInstance.action( key, "ConstraintValidator" ) );
        if ( constraintValidator instanceof DaoContextAwareConstraintValidator) {
            ( (DaoContextAwareConstraintValidator) constraintValidator ).setDaoContext( daoContext );
        }
        return constraintValidator;
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        // noop
    }

    /**
     * Runs the given privileged action, using a privileged block if required.
     * <p>
     * <b>NOTE:</b> This must never be changed into a publicly available method to avoid execution of arbitrary
     * privileged actions within HV's protection domain.
     */
    private <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged( action ) : action.run();
    }
}
package solvas.rest.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import solvas.persistence.api.DaoContext;

/**
 * ConstraintValidator that can access the persistence layer
 */
public class DaoContextAwareConstraintValidator {
    private DaoContext daoContext;

    public DaoContext getDaoContext() {
        return daoContext;
    }

    public void setDaoContext(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    public DaoContextAwareConstraintValidator(DaoContext context) { daoContext = context;}
}
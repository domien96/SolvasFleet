package solvas.rest.utils.validators;

import solvas.persistence.api.DaoContext;

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
}
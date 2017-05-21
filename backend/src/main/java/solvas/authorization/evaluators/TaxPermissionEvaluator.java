package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Tax;

import static solvas.authorization.ApiPermissionStrings.WRITE_TAXES;

/**
 * Evaluate permissions for taxes.
 *
 * @author Niko Strijbol
 */
public class TaxPermissionEvaluator extends AbstractPermissionEvaluator<Tax> {

    /**
     * @param dao The dao for the model.
     */
    public TaxPermissionEvaluator(Dao<Tax> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Tax model) {
        return true;
    }

    @Override
    public boolean canCreate(Authentication authentication, Tax model) {
        return hasScope(authentication, WRITE_TAXES);
    }

    @Override
    public boolean canEdit(Authentication authentication, Tax model) {
        return hasScope(authentication, WRITE_TAXES);
    }

    @Override
    public boolean canDelete(Authentication authentication, Tax model) {
        return hasScope(authentication, WRITE_TAXES);
    }
}
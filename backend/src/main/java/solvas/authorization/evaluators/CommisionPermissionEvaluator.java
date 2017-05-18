package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Commission;

import static solvas.authorization.ApiPermissionStrings.READ_COMMISSIONS;
import static solvas.authorization.ApiPermissionStrings.WRITE_COMMISSIONS;

/**
 * @author Niko Strijbol
 */
public class CommisionPermissionEvaluator extends AbstractPermissionEvaluator<Commission> {

    /**
     * @param dao The dao for the model.
     */
    public CommisionPermissionEvaluator(Dao<Commission> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Commission model) {
        return hasScope(authentication, READ_COMMISSIONS);
    }

    @Override
    public boolean canCreate(Authentication authentication, Commission model) {
        return hasScope(authentication, WRITE_COMMISSIONS);
    }

    @Override
    public boolean canEdit(Authentication authentication, Commission model) {
        return hasScope(authentication, WRITE_COMMISSIONS);
    }

    @Override
    public boolean canDelete(Authentication authentication, Commission model) {
        return hasScope(authentication, WRITE_COMMISSIONS);
    }
}
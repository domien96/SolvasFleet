package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Revision;

import static solvas.authorization.ApiPermissionStrings.READ_REVISIONS;

/**
 * Evaluate permissions for revisions.
 *
 * @author Niko Strijbol
 */
public class RevisionPermissionEvaluator extends AbstractPermissionEvaluator<Revision> {

    /**
     * @param dao The dao for the model.
     */
    public RevisionPermissionEvaluator(Dao<Revision> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Revision model) {
        return hasScope(authentication, READ_REVISIONS);
    }

    @Override
    public boolean canCreate(Authentication authentication, Revision model) {
        return false;
    }

    @Override
    public boolean canEdit(Authentication authentication, Revision model) {
        return false;
    }

    @Override
    public boolean canDelete(Authentication authentication, Revision model) {
        return false;
    }
}
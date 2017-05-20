package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.authentication.user.Authority;
import solvas.persistence.api.Dao;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Base class for evaluating permissions.
 *
 * @param <M> The type of the model.
 */
public abstract class AbstractPermissionEvaluator<M extends Model> implements PermissionEvaluator {
    private Map<String, PermissionDecider<M>> deciders = new HashMap<>();

    {
        registerPermissionDecider("READ", this::canRead);
        registerPermissionDecider("CREATE", this::canCreate);
        registerPermissionDecider("EDIT", this::canEdit);
        registerPermissionDecider("DELETE", this::canDelete);
    }

    private final Dao<M> dao;

    /**
     * @param dao The dao for the model.
     */
    public AbstractPermissionEvaluator(Dao<M> dao) {
        this.dao = dao;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, Object permission) {
        M model;
        try {
            model = dao.find((Integer) targetId);
        } catch (EntityNotFoundException e) {
            model = null;
        }

        return deciders.getOrDefault(permission, this::reject).decide(authentication, model);
    }

    /**
     * Check if the current user can read the model.
     *
     * @param authentication The authentication.
     * @param model The model to check.
     *
     * @return True if the user has permission.
     */
    public abstract boolean canRead(Authentication authentication, M model);

    /**
     * Check if the current user can create the model.
     *
     * @param authentication The authentication.
     * @param model The model to check.
     *
     * @return True if the user has permission.
     */
    public abstract boolean canCreate(Authentication authentication, M model);

    /**
     * Check if the current user can edit the model.
     *
     * @param authentication The authentication.
     * @param model The model to check.
     *
     * @return True if the user has permission.
     */
    public abstract boolean canEdit(Authentication authentication, M model);

    /**
     * Check if the current user can delete/archive the model.
     *
     * @param authentication The authentication.
     * @param model The model to check.
     *
     * @return True if the user has permission.
     */
    public abstract boolean canDelete(Authentication authentication, M model);

    /**
     * Add a permission decider.
     *
     * @param permission The permission.
     * @param decider The decider.
     */
    public void registerPermissionDecider(String permission, PermissionDecider<M> decider) {
        deciders.put(permission, decider);
    }

    /**
     * Always reject a permission.
     *
     * @param authentication The authentication.
     * @param model The model to reject.
     *
     * @return False.
     */
    public boolean reject(Authentication authentication, M model) {
        return false;
    }

    /**
     * Check if the scope applies. This means the user has permission for the given company, or has a global
     * permission.
     *
     * @param authentication The authentication.
     * @param scope The company-specific scope.
     * @param forCompanies The ID of the company.
     * @param globalScope The global permission.
     *
     * @return True if the user has the scope.
     */
    protected boolean hasScope(Authentication authentication,
                               String scope,
                               int forCompanies,
                               String globalScope) {
        return hasScope(authentication, scope, forCompanies) || hasScope(authentication, globalScope);
    }


    /**
     * Check if the user has a scope.
     *
     * @param authentication The authentication.
     * @param scope The scope.
     *
     * @return True if the user has a scope.
     */
    protected static boolean hasScope(Authentication authentication, String scope) {
        return hasScope(getAuthorities(authentication), scope);
    }

    public static boolean hasScope(Stream<Authority> authorities, String scope) {
        return authorities.map(Authority::getScopes)
                .flatMap(Collection::stream)
                .anyMatch(scope::equals);
    }

    /**
     * Check if the user has a scope for a company.
     *
     * @param authentication The authentication.
     * @param scope The company-specific scope.
     * @param forCompanies The ID of the company.
     *
     * @return True if the user has the scope.
     */
    protected static boolean hasScope(Authentication authentication, String scope, Integer forCompanies) {
        return hasScope(
                getAuthorities(authentication).filter(authority -> forCompanies.equals(authority.getCompanyId())),
                scope);
    }

    private static Stream<Authority> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .filter(Authority.class::isInstance)
                .map(Authority.class::cast);
    }


    /**
     * Interface for deciding on permissions.
     *
     * @param <M> The type of the model.
     */
    @FunctionalInterface
    protected interface PermissionDecider<M extends Model> {

        /**
         * Decide on a permission.
         *
         * @param authentication The authentication.
         * @param model The model.
         *
         * @return True if the user has permission.
         */
        boolean decide(Authentication authentication, M model);
    }
}

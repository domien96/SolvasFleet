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

public abstract class AbstractPermissionEvaluator<M extends Model> implements PermissionEvaluator {
    private Map<String, PermissionDecider<M>> deciders = new HashMap<>();

    {
        registerPermissionDecider("READ", this::canRead);
        registerPermissionDecider("CREATE", this::canCreate);
        registerPermissionDecider("EDIT", this::canEdit);
        registerPermissionDecider("DELETE", this::canDelete);
    }

    private final Dao<M> dao;

    public AbstractPermissionEvaluator(Dao<M> dao) {
        this.dao = dao;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, Object permission) {
        try {
            return deciders.getOrDefault(permission, this::reject)
                    .decide(authentication, dao.find((Integer) targetId));
        } catch (EntityNotFoundException e) {
            return reject(authentication, null);
        }
    }

    public abstract boolean canRead(Authentication authentication, M model);

    public abstract boolean canCreate(Authentication authentication, M model);

    public abstract boolean canEdit(Authentication authentication, M model);

    public abstract boolean canDelete(Authentication authentication, M model);

    public void registerPermissionDecider(String permission, PermissionDecider<M> decider) {
        deciders.put(permission, decider);
    }

    public boolean reject(Authentication authentication, M model) {
        return false;
    }

    protected boolean hasScope(Authentication authentication,
                               String scope,
                               int forCompanies,
                               String globalScope) {
        return hasScope(authentication, scope, forCompanies) || hasScope(authentication, globalScope);
    }


    protected boolean hasScope(Authentication authentication, String scope) {
        return hasScope(getAuthorities(authentication), scope);
    }

    private boolean hasScope(Stream<Authority> authorities, String scope) {
        return authorities.map(Authority::getScopes)
                .flatMap(Collection::stream)
                .anyMatch(scope::equals);
    }

    protected boolean hasScope(Authentication authentication, String scope, Integer forCompanies) {
        return hasScope(
                getAuthorities(authentication).filter(authority -> forCompanies.equals(authority.getCompanyId())),
                scope);
    }

    private Stream<Authority> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .filter(Authority.class::isInstance)
                .map(Authority.class::cast);
    }


    @FunctionalInterface
    protected interface PermissionDecider<M extends Model> {
        boolean decide(Authentication authentication, M model);
    }
}

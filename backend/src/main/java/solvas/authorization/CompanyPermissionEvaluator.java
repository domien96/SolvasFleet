package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import solvas.authentication.user.Authority;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.Company;
import solvas.service.models.Fleet;

import java.io.Serializable;
import java.util.Collection;

/**
 * Check if the current user meets a certain permission criteria
 */
public class CompanyPermissionEvaluator implements PermissionEvaluator {
    private final ResolverContext resolverContext;

    public CompanyPermissionEvaluator(ResolverContext resolverContext) {
        this.resolverContext = resolverContext;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false; // TODO
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Collection<Integer>  companyIds;
        try {
            companyIds = getCompanyIds((Integer) targetId, targetType);
        } catch (EntityNotFoundException e) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .filter(Authority.class::isInstance)
                .map(Authority.class::cast)
                .filter(authority -> companyIds.contains(authority.getCompanyId()))
                .flatMap(a -> a.getPermissions().stream())
                .anyMatch(p -> p.getAction().equals(permission) && p.getResource().equals(targetType));
    }

    private Collection<Integer> getCompanyIds(int targetId, String targetType) throws EntityNotFoundException {
        return resolverContext.getResolver(targetType).resolve(targetId);
    }
}

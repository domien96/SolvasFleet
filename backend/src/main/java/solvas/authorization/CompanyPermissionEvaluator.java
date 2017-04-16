package solvas.authorization;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import solvas.authentication.user.Authority;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Check if the current user meets a certain permission criteria
 */
public class CompanyPermissionEvaluator implements PermissionEvaluator {
    private final CompanyResolverContext resolverContext;

    public CompanyPermissionEvaluator(CompanyResolverContext resolverContext) {
        this.resolverContext = resolverContext;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if(! (targetDomainObject instanceof  ApiModel)) {
            throw new UnsupportedOperationException(String.format("Could not check permissions for target object of class %s.", targetDomainObject.getClass()));
        }
        ApiModel o = (ApiModel)targetDomainObject;

        return hasPermission(authentication, o.getId(), resolverContext.getResourceType(o), permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Collection<Integer>  companyIds = getCompanyIds((Integer) targetId, targetType);

        return authentication.getAuthorities().stream()
                .filter(Authority.class::isInstance)
                .map(Authority.class::cast)
                .filter(authority -> companyIds.contains(authority.getCompanyId()))
                .flatMap(a -> a.getPermissions().stream())
                .anyMatch(p -> p.getAction().equals(permission) && p.getResource().equals(targetType));
    }

    private Collection<Integer> getCompanyIds(int targetId, String targetType) {
        Collection<Integer> ids;
        try {
            ids = resolverContext.getResolver(targetType).resolve(targetId);
        } catch (EntityNotFoundException e) {
            ids = new ArrayList<>();
        }
        ids.add(0); // Company id 0 means permission is granted for all companies
        return ids;
    }
}

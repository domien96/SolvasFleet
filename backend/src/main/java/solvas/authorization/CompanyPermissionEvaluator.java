package solvas.authorization;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import solvas.authentication.user.Authority;
import solvas.service.models.Company;
import solvas.service.models.Fleet;

import java.io.Serializable;

/**
 * Created by david on 02/04/17.
 */
public class CompanyPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        int companyId = getCompanyId((Integer) targetId, targetType);
        return authentication.getAuthorities().stream()
                .filter(Authority.class::isInstance)
                .map(Authority.class::cast)
                .filter(authority -> authority.getCompanyId().equals(companyId))
                .anyMatch(authority -> authority.getPermissions().contains(permission));
    }

    private int getCompanyId(int targetId, String targetType) {
        if(targetType.equals("Company")) {
            return targetId;
        } else {
            throw new RuntimeException("target type not supported"); // TODO
        }
    }

    private int getCompanyId(Company company) {
        return company.getId();
    }

    private int getCompanyId(Fleet fleet) {
        return fleet.getCompany().getId();
    }

    private int getCompanyId(Object object) {
        throw new RuntimeException("Can't resolve company for this entity");
    }
}

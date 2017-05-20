package solvas.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import solvas.authentication.user.Authority;
import solvas.authorization.evaluators.AbstractPermissionEvaluator;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CompanyExtractor {
    public Collection<Integer> extractCompanyIds(Authentication auth) {
        String scopedPermission = ApiPermissionStrings.READ_COMPANY;
        String globalPermission = ApiPermissionStrings.READ_COMPANIES;
        if (AbstractPermissionEvaluator.hasScope(getAuthorities(auth), globalPermission)) {
            return null;
        }

        return getAuthorities(auth).filter(a -> a.getScopes().contains(scopedPermission))
                .map(Authority::getCompanyId)
                .collect(Collectors.toList());
    }

    private static Stream<Authority> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .filter(Authority.class::isInstance)
                .map(Authority.class::cast);
    }
}

package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Configure the authorization
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    /**
     * @param resolverContext Context to resolve company id's for resources
     * @return The evaluator to check if a user has permissions
     */
    @Bean
    @Autowired
    public PermissionEvaluator permissionEvaluator(CompanyResolverContext resolverContext) {
        return new CompanyPermissionEvaluator(resolverContext);
    }
}

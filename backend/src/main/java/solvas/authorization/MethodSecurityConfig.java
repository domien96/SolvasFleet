package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Configure the authorization
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    private final PermissionEvaluatorContext permissionEvaluatorContext;

    @Autowired
    public MethodSecurityConfig(PermissionEvaluatorContext permissionEvaluatorContext) {
        this.permissionEvaluatorContext = permissionEvaluatorContext;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator(permissionEvaluatorContext));
        return expressionHandler;
    }

    /**
     * @param permissionEvaluatorContext Context to look up evaluators for different models
     * @return The evaluator to check if a user has permissions
     */
    @Bean
    @Autowired
    public PermissionEvaluator permissionEvaluator(PermissionEvaluatorContext permissionEvaluatorContext) {
        return new SolvasPermissionEvaluator(permissionEvaluatorContext);
    }
}

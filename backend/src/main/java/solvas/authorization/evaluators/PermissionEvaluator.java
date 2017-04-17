package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;

import java.io.Serializable;

public interface PermissionEvaluator {
    boolean hasPermission(Authentication authentication, Serializable targetId, Object permission);
}

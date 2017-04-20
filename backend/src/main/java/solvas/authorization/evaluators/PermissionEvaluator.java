package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Evaluate permissions for a model.
 */
@FunctionalInterface
public interface PermissionEvaluator {

    /**
     * Check if the user has permission for a target.
     *
     * @param authentication The authentication.
     * @param targetId The target ID.
     * @param permission The permission.
     *
     * @return True if the user has permission.
     */
    boolean hasPermission(Authentication authentication, Serializable targetId, Object permission);
}
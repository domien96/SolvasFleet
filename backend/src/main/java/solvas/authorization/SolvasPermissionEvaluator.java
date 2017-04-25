package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import solvas.authentication.user.Authority;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;
import solvas.service.models.Permission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Check if the current user meets a certain permission criteria
 */
@Component
public class SolvasPermissionEvaluator implements PermissionEvaluator {
    private final PermissionEvaluatorContext permissionEvaluatorContext;

    /**
     * Create instance
     * @param permissionEvaluatorContext Context to look up evaluators for different models
     */
    @Autowired
    public SolvasPermissionEvaluator(PermissionEvaluatorContext permissionEvaluatorContext) {
        this.permissionEvaluatorContext = permissionEvaluatorContext;
    }


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if(! (targetDomainObject instanceof  ApiModel)) {
            throw new UnsupportedOperationException(String.format("Could not check permissions for target object of class %s.", targetDomainObject.getClass()));
        }
        ApiModel o = (ApiModel)targetDomainObject;

        return hasPermission(authentication, o.getId(), permissionEvaluatorContext.getResourceType(o), permission);
      }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return permissionEvaluatorContext.getEvaluator(targetType).hasPermission(authentication, targetId, permission);
    }
}

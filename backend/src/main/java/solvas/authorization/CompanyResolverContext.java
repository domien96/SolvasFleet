package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authorization.exceptions.CompanyResolvingException;
import solvas.authorization.resolvers.*;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Context to find {@link CompanyIdResolver} for a resource
 */
@Component
public class CompanyResolverContext {
    private final Map<String, CompanyIdResolver> resolvers = new HashMap<>();
    private final Map<Class<? extends ApiModel>, String> resourceNames =
            new HashMap<Class<? extends ApiModel>, String>() {{
                put(ApiVehicle.class, "vehicle");
                put(ApiCompany.class, "company");
                put(ApiFleet.class, "fleet");
                put(ApiUser.class, "user");
                put(ApiRole.class, "role");
                put(ApiFunction.class, "function");
                put(ApiPermission.class, "permission");
            }};

    /**
     * Create instance
     * @param daoContext Context to pass Dao's to resolvers
     */
    @Autowired
    public CompanyResolverContext(DaoContext daoContext) {
        resolvers.put("fleet", new FleetToCompanyIdResolver(daoContext.getFleetDao()));
        resolvers.put("company", new CompanyToCompanyIdResolver());
        resolvers.put("vehicle", new VehicleToCompanyIdResolver(daoContext.getVehicleDao()));
        resolvers.put("user", new NoCompanyResolver());
        resolvers.put("role", new NoCompanyResolver());
        resolvers.put("permission", new NoCompanyResolver());
        resolvers.put("function", new NoCompanyResolver());
    }

    /**
     * Get resolver for a resource type
     * @param resource name of the resource type
     * @return The CompanyIdResolver
     */
    public CompanyIdResolver getResolver(String resource) {
        if (!resolvers.containsKey(resource)) {
            throw new CompanyResolvingException(String.format("Could not resolve company for resource type %s.", resource));
        }
        return resolvers.get(resource);
    }

    /**
     * Get resource type name for a resource
     * @param resource Resource to get type name for
     * @return The type name
     */
    public String getResourceType(ApiModel resource) {
        return resourceNames.get(resource.getClass());
    }
}

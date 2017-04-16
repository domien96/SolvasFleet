package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authorization.exceptions.CompanyResolvingException;
import solvas.authorization.resolvers.*;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.*;

import java.util.HashMap;
import java.util.Map;

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

    public CompanyIdResolver getResolver(String resource) {
        if (!resolvers.containsKey(resource)) {
            throw new CompanyResolvingException(String.format("Could not resolve company for resource type %s.", resource));
        }
        return resolvers.get(resource);
    }

    public String getResourceType(ApiModel resource) {
        return resourceNames.get(resource.getClass());
    }
}

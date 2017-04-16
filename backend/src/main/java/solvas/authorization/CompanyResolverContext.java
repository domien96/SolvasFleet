package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authorization.exceptions.CompanyResolvingException;
import solvas.authorization.resolvers.CompanyIdResolver;
import solvas.authorization.resolvers.CompanyToCompanyIdResolver;
import solvas.authorization.resolvers.FleetToCompanyIdResolver;
import solvas.authorization.resolvers.VehicleToCompanyIdResolver;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiFleet;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiVehicle;

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
            }};

    @Autowired
    public CompanyResolverContext(DaoContext daoContext) {
        resolvers.put("fleet", new FleetToCompanyIdResolver(daoContext.getFleetDao()));
        resolvers.put("company", new CompanyToCompanyIdResolver());
        resolvers.put("vehicle", new VehicleToCompanyIdResolver(daoContext.getVehicleDao()));
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

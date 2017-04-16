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
    public final static String VEHICLE_RESOURCE_TYPE = "vehicle";
    public final static String COMPANY_RESOURCE_TYPE = "company";
    public final static String FLEET_RESOURCE_TYPE = "fleet";
    public final static String USER_RESOURCE_TYPE = "user";
    public final static String ROLE_RESOURCE_TYPE = "role";
    public final static String FUNCTION_RESOURCE_TYPE = "function";
    public final static String PERMISSION_RESOURCE_TYPE = "permission";
    public final static String INVOICE_RESOURCE_TYPE = "invoice";
    public final static String CONTRACT_RESOURCE_TYPE = "contract";

    private final Map<String, CompanyIdResolver> resolvers = new HashMap<>();
    private final Map<Class<? extends ApiModel>, String> resourceNames =
            new HashMap<Class<? extends ApiModel>, String>() {{
                put(ApiVehicle.class, VEHICLE_RESOURCE_TYPE);
                put(ApiCompany.class, COMPANY_RESOURCE_TYPE);
                put(ApiFleet.class, FLEET_RESOURCE_TYPE);
                put(ApiUser.class, USER_RESOURCE_TYPE);
                put(ApiRole.class, ROLE_RESOURCE_TYPE);
                put(ApiFunction.class, FUNCTION_RESOURCE_TYPE);
                put(ApiPermission.class, PERMISSION_RESOURCE_TYPE);
                put(ApiInvoice.class, INVOICE_RESOURCE_TYPE);
                put(ApiContract.class, CONTRACT_RESOURCE_TYPE);
            }};

    /**
     * Create instance
     * @param daoContext Context to pass Dao's to resolvers
     */
    @Autowired
    public CompanyResolverContext(DaoContext daoContext) {
        resolvers.put(FLEET_RESOURCE_TYPE, new FleetToCompanyIdResolver(daoContext.getFleetDao()));
        resolvers.put(COMPANY_RESOURCE_TYPE, new CompanyToCompanyIdResolver());
        resolvers.put(VEHICLE_RESOURCE_TYPE, new VehicleToCompanyIdResolver(daoContext.getVehicleDao()));
        resolvers.put(USER_RESOURCE_TYPE, new NoCompanyResolver());
        resolvers.put(ROLE_RESOURCE_TYPE, new NoCompanyResolver());
        resolvers.put(PERMISSION_RESOURCE_TYPE, new NoCompanyResolver());
        resolvers.put(FUNCTION_RESOURCE_TYPE, new NoCompanyResolver());
        resolvers.put(INVOICE_RESOURCE_TYPE, new InvoiceToCompanyIdResolver(daoContext.getInvoiceDao()));
        resolvers.put(CONTRACT_RESOURCE_TYPE, new ContractToCompanyIdResolver(daoContext.getContractDao()));
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

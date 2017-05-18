package solvas.authorization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authorization.evaluators.*;
import solvas.authorization.exceptions.UnsupportedResourceType;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Context for the permission evaluator.
 */
@Component
public class PermissionEvaluatorContext {
    public final static String VEHICLE_RESOURCE_TYPE = "vehicle";
    public final static String COMPANY_RESOURCE_TYPE = "company";
    public final static String FLEET_RESOURCE_TYPE = "fleet";
    public final static String USER_RESOURCE_TYPE = "user";
    public final static String ROLE_RESOURCE_TYPE = "role";
    public final static String FUNCTION_RESOURCE_TYPE = "function";
    public final static String PERMISSION_RESOURCE_TYPE = "permission";
    public final static String INVOICE_RESOURCE_TYPE = "invoice";
    public final static String CONTRACT_RESOURCE_TYPE = "contract";
    public final static String REVISION_RESOURCE_TYPE = "revision";

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
                put(ApiRevision.class, REVISION_RESOURCE_TYPE);
            }};

    private Map<String, PermissionEvaluator> evaluators = new HashMap<>();

    /**
     * @param daoContext Autowired dao.
     */
    @Autowired
    public PermissionEvaluatorContext(DaoContext daoContext) {
        evaluators.put(COMPANY_RESOURCE_TYPE, new CompanyPermissionEvaluator(daoContext.getCompanyDao()));
        evaluators.put(VEHICLE_RESOURCE_TYPE, new VehiclePermissionEvaluator(daoContext.getVehicleDao()));
        evaluators.put(FLEET_RESOURCE_TYPE, new FleetPermissionEvaluator(daoContext.getFleetDao()));
        evaluators.put(USER_RESOURCE_TYPE, new UserPermissionEvaluator(daoContext.getUserDao()));
        evaluators.put(ROLE_RESOURCE_TYPE, new RolePermissionEvaluator(daoContext.getRoleDao()));
        evaluators.put(FUNCTION_RESOURCE_TYPE, new FunctionPermissionEvaluator(daoContext.getFunctionDao()));
        evaluators.put(PERMISSION_RESOURCE_TYPE, new PermissionPermissionEvaluator(daoContext.getPermissionDao()));
        evaluators.put(INVOICE_RESOURCE_TYPE, new InvoicePermissionEvaluator(daoContext.getInvoiceDao()));
        evaluators.put(CONTRACT_RESOURCE_TYPE, new ContractPermissionEvaluator(daoContext.getContractDao()));
        evaluators.put(REVISION_RESOURCE_TYPE, new RevisionPermissionEvaluator(daoContext.getRevisionDao()));
    }

    /**
     * Get resource type name for a resource
     * @param resource Resource to get type name for
     * @return The type name
     */
    public String getResourceType(ApiModel resource) {
        if(! resourceNames.containsKey(resource.getClass())) {
            throw new UnsupportedResourceType(String.format("Unsupported resource class %s.", resource.getClass()));
        }

        return resourceNames.get(resource.getClass());
    }

    /**
     * Get evaluator for a resource type.
     *
     * @param resourceType The resource type.
     *
     * @return The evaluator.
     */
    public PermissionEvaluator getEvaluator(String resourceType) {
        if(! evaluators.containsKey(resourceType)) {
            throw new UnsupportedResourceType(String.format("Unsupported resource type %s.", resourceType));
        }

        return evaluators.get(resourceType);
    }
}

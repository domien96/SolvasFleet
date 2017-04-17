package solvas.authorization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authorization.evaluators.CompanyPermissionEvaluator;
import solvas.authorization.evaluators.PermissionEvaluator;
import solvas.authorization.exceptions.UnsupportedResourceType;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.*;

import java.util.HashMap;
import java.util.Map;

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

    private Map<String, PermissionEvaluator> evaluators = new HashMap<>();

    @Autowired
    public PermissionEvaluatorContext(DaoContext daoContext) {
        evaluators.put(COMPANY_RESOURCE_TYPE, new CompanyPermissionEvaluator(daoContext.getCompanyDao()));
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

    public PermissionEvaluator getEvaluator(String resourceType) {
        if(! evaluators.containsKey(resourceType)) {
            throw new UnsupportedResourceType(String.format("Unsupported resource type %s.", resourceType));
        }

        return evaluators.get(resourceType);
    }
}

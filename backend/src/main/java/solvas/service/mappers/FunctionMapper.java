package solvas.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.SimpleUrlBuilder;
import solvas.rest.api.models.ApiFunction;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Company;
import solvas.service.models.Function;
import solvas.service.models.Role;
import solvas.service.models.User;

/**
 * Map functions in the persistence layer and functions in the api layer (bidirectionally)
 */
@Component
public class FunctionMapper extends AbstractMapper<Function, ApiFunction> {

    /**
     * Create instance
     * @param daoContext DaoContext used
     */
    @Autowired
    public FunctionMapper(DaoContext daoContext) {
        super(daoContext, "startDate", "endDate");
    }

    @Override
    public Function convertToModel(ApiFunction api) throws DependantEntityNotFound, EntityNotFoundException {
        Company company = null;
        User user;
        Role role;
        try {
            if (api.getCompany() != -1) {
                company = daoContext.getCompanyDao().find(api.getCompany());
            }
            user = daoContext.getUserDao().find(api.getUser());
            role = daoContext.getRoleDao().find(api.getRole());
        } catch (EntityNotFoundException e) {
            throw new DependantEntityNotFound("Can't find related entity", e);
        }

        Function function = daoContext.getFunctionDao().find(api.getId());
        function.setCompany(company);
        function.setRole(role);
        function.setUser(user);

        copySharedAttributes(function, api);

        return function;
    }

    @Override
    public ApiFunction convertToApiModel(Function model) {
        ApiFunction apiFunction = new ApiFunction();

        copySharedAttributes(apiFunction, model);
        copyAttributes(apiFunction, model, "id");

        apiFunction.setCompany(model.getCompany().getId());
        apiFunction.setRole(model.getRole().getId());
        apiFunction.setUser(model.getUser().getId());

        return apiFunction;
    }
}

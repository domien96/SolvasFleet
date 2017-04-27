package solvas.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiFunction;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Company;
import solvas.service.models.Function;
import solvas.service.models.Role;
import solvas.service.models.User;

import java.time.LocalDateTime;

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
        super(daoContext,  "endDate");
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

        Function function = api.getId()==0?new Function():daoContext.getFunctionDao().find(api.getId());
        function.setCompany(company);
        function.setRole(role);
        function.setUser(user);
        function.setStartDate(api.getStartDate()==null? LocalDateTime.now():api.getStartDate());

        copySharedAttributes(function, api);

        return function;
    }

    @Override
    public ApiFunction convertToApiModel(Function model) {
        ApiFunction apiFunction = new ApiFunction();

        copySharedAttributes(apiFunction, model);
        copyAttributes(apiFunction, model, "id", "createdAt", "updatedAt");

        if(model.getCompany() == null) {
            apiFunction.setCompany(-1);
        } else {
            apiFunction.setCompany(model.getCompany().getId());
        }
        apiFunction.setRole(model.getRole().getId());
        apiFunction.setUser(model.getUser().getId());
        apiFunction.setStartDate(model.getStartDate());

        return apiFunction;
    }
}

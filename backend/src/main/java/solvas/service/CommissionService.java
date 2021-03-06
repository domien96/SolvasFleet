package solvas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.CommissionDao;
import solvas.rest.api.models.ApiCommission;
import solvas.rest.query.CommissionFilter;
import solvas.service.mappers.CommissionMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Commission;

/**
 * Service class for commission. It contains the priority logic.
 * @author Domien
 */
@Service
public class CommissionService extends AbstractService<Commission,ApiCommission> {
    /**
     * Construct an abstractservice
     *
     * @param modelDao the DAO of the model
     * @param mapper   the mapper between the apimodel and the model
     */
    public CommissionService(CommissionDao modelDao, CommissionMapper mapper) {
        super(modelDao, mapper);
    }

    /**
     * Finds the commission which suits best to the given filter parameters.
     * This means that if no commission is found for the given parameters, then a commission for a more general
     * set of parameters will be used (a higher level commission).
     *
     * For example suppose we want to find the commission for a specific insurance type, vehicle type, a fleet and a company.
     * If no record is found, then this method will look for a commission for any vehicle type but still for the given
     * insurance type, fleet and company.
     *
     * This process will continue until a record is found. If there is no commission found at the top level then nothing
     * will be returned.
     * @param pagination pagination information
     * @param filters the parameters to filter on
     * @return The commission corresponding to the given set of parameters. The page should normally contain
     *         at most 1 object.
     */
    public Page<ApiCommission> findBottomUp(Pageable pagination, Filter<Commission> filters) {
        CommissionFilter f = (CommissionFilter) filters;

        // 1st priority : vehicle specific
        Page<Commission> result = modelDao.findAll(filters,pagination);
        if(result.hasContent())
            return result.map(s -> mapper.convertToApiModel(s));

        // 3rd priority : fleet specific
        f.setVehicle(-1);
        result = modelDao.findAll(filters,pagination);
        if(result.hasContent())
            return result.map(s -> mapper.convertToApiModel(s));

        // 3rd priority : company specific
        f.setFleet(-1);
        result = modelDao.findAll(filters,pagination);
        if(result.hasContent())
            return result.map(s -> mapper.convertToApiModel(s));

        // 4th priority : vehicle type specific
        f.setCompany(-1);
        result = modelDao.findAll(filters,pagination);
        if(result.hasContent())
            return result.map(s -> mapper.convertToApiModel(s));

        // last priority : insurance type specific
        f.setVehicleType(null);
        result = modelDao.findAll(filters,pagination);
        return result.map(s -> mapper.convertToApiModel(s));
    }

    @Override
    public Page<ApiCommission> findAll(Pageable pagination, Filter<Commission> filters) {
        return findBottomUp(pagination, filters);
    }

    @Override
    public ApiCommission update(int id,ApiCommission input) throws DependantEntityNotFound, EntityNotFoundException {
        // Check whether a commission exclusively exists for the given filter parameters
        // If not, then we create a new commission record. Else we just update the already existing one.
        // This way we don't change commission on a higher level.

        Pageable pagination = new PageRequest(0, 10); // dummy pagination object so we can call findBottomUp
        Page<ApiCommission> res = super.findAll(pagination,apiCommissionAsFilter(input));
        if(res.hasContent()) {
            assert(res.getContent().size() == 1); // unique set of parameters
            id = res.getContent().get(0).getId();
            input.setId(id);
            return super.update(id,input);
        } else {
            input.setId(0); // makes sure a new record is actually created
            return super.create(input);
        }
    }

    private Filter<Commission> apiCommissionAsFilter(ApiCommission api) {
        CommissionFilter commissionFilter = new CommissionFilter();
        commissionFilter.setInsuranceType(api.getInsuranceType());
        commissionFilter.setCompany(api.getCompany());
        commissionFilter.setFleet(api.getFleet());
        commissionFilter.setVehicleType(api.getVehicleType());
        commissionFilter.setVehicle(api.getVehicle());
        return commissionFilter;
    }
}

package solvas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import solvas.persistence.api.Dao;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.CommissionDao;
import solvas.rest.api.models.ApiCommission;
import solvas.rest.query.CommissionFilter;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.CommissionMapper;
import solvas.service.models.Commission;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by steve on 13/05/2017.
 */
@Service
public class CommissionService extends AbstractService<Commission,ApiCommission> {
    /**
     * Contruct an abstractservice
     *
     * @param modelDao the DAO of the model
     * @param mapper   the mapper between the apimodel and the model
     */
    public CommissionService(CommissionDao modelDao, CommissionMapper mapper) {
        super(modelDao, mapper);
    }

    @Override
    public Page<ApiCommission> findAll(Pageable pagination, Filter<Commission> filters) {
        CommissionFilter f = (CommissionFilter) filters;

        // 1st priority : vehicle specific
        Page<Commission> result = modelDao.findAll(filters,pagination);
        if(result.hasContent())
            return result.map(s -> mapper.convertToApiModel(s));

        // 2nd priority : vehicle type specific
        f.setVehicle(-1);
        result = modelDao.findAll(filters,pagination);
        if(result.hasContent())
            return result.map(s -> mapper.convertToApiModel(s));

        // 3rd priority : fleet specific
        f.setVehicleType(null);
        result = modelDao.findAll(filters,pagination);
        if(result.hasContent())
            return result.map(s -> mapper.convertToApiModel(s));

        // 4th priority : company specific
        f.setFleet(-1);
        result = modelDao.findAll(filters,pagination);
        if(result.hasContent())
            return result.map(s -> mapper.convertToApiModel(s));

        // last priority : insurance type specific
        f.setCompany(-1);
        result = modelDao.findAll(filters,pagination);
        return result.map(s -> mapper.convertToApiModel(s));
    }
}

package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.InsuranceTypeDao;
import solvas.rest.api.models.ApiInsurance;
import solvas.service.mappers.AbstractMapper;
import solvas.service.models.Insurance;
import solvas.service.models.InsuranceType;

/**
 * Created by domien on 1/04/2017.
 */
@Service
public class InsuranceService extends AbstractService<Insurance,ApiInsurance> {
    private final InsuranceTypeDao insuranceTypeDao;

    /**
     * Contruct an abstractservice
     *
     * @param context the DAO context
     */
    @Autowired
    public InsuranceService(DaoContext context) {
        super(context.getInsuranceDao(), null);
        this.insuranceTypeDao = context.getInsuranceTypeDao();
    }

    /**
     * Find all different possible contract types.
     * @param pagination The pagination information.
     * @param filters The filters.
     * @return the paged representation of the contracttypes as strings
     */
    public Page<String> findAllInsuranceTypes(Pageable pagination, Filter<InsuranceType> filters) {
        return insuranceTypeDao.findAll(filters,pagination).map(s -> s.getName());
    }
}

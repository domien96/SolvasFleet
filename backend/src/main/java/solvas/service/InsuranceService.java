package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.InsuranceTypeDao;
import solvas.rest.api.models.ApiInsurance;
import solvas.service.models.Insurance;
import solvas.service.models.InsuranceType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Service for insurances.
 *
 * @author Domien
 */
@Service
public class InsuranceService extends AbstractService<Insurance, ApiInsurance> {

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
     * Find all contract types.
     *
     * @return The contract types.
     */
    public Collection<String> findAllInsuranceTypes() {
        return insuranceTypeDao.findAll()
                .stream()
                .map(InsuranceType::getName)
                .collect(Collectors.toList());
    }
}
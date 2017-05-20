package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiCommission;
import solvas.service.models.Commission;
import solvas.service.models.InsuranceType;
import solvas.service.models.VehicleType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by steve on 13/05/2017.
 */
@Component
public class CommissionMapper  extends AbstractMapper<Commission,ApiCommission> {


    /**
     * Create a mapper between Tax and ApiTax
     *
     * @param daoContext The context for Dao's
     */
    public CommissionMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Commission convertToModel(ApiCommission api) throws EntityNotFoundException {
        Commission commission = api.getId()==0? new Commission():daoContext.getCommissionDao().find(api.getId());
        commission.setCompany(api.getCompany()>0 ? daoContext.getCompanyDao().find(api.getCompany()):null);
        commission.setFleet(api.getFleet()>0 ? daoContext.getFleetDao().find(api.getFleet()):null);
        commission.setVehicle(api.getVehicle()>0 ? daoContext.getVehicleDao().find(api.getVehicle()):null);


        Optional<VehicleType> vehicleType = daoContext.getVehicleTypeDao().findByName(api.getVehicleType());
        if (vehicleType.isPresent()){
            commission.setVehicleType(vehicleType.get());

        } else {
            commission.setVehicleType(null);
        }

        commission.setValue(BigDecimal.valueOf(api.getValue()).divide(BigDecimal.valueOf(100)));

        Optional<InsuranceType> insuranceType = daoContext.getInsuranceTypeDao().findByName(api.getInsuranceType());
        if (insuranceType.isPresent()){
            commission.setInsuranceType(insuranceType.get());

        } else {
            throw new EntityNotFoundException();
        }
        return commission;
    }

    @Override
    public ApiCommission convertToApiModel(Commission model) {
        ApiCommission api = new ApiCommission();
        copyAttributes(api, model, "id", "createdAt", "updatedAt");
        copySharedAttributes(api, model);

        api.setCompany(model.getCompany()!=null?model.getCompany().getId():0);
        api.setFleet(model.getFleet()!=null?model.getFleet().getId():0);
        api.setVehicle(model.getVehicle()!=null?model.getVehicle().getId():0);

        api.setValue(model.getValue().multiply(BigDecimal.valueOf(100)).longValue());
        api.setVehicleType(model.getVehicleType()!=null?model.getVehicleType().getName():null);
        api.setInsuranceType(model.getInsuranceType().getName());


        return api;
    }
}
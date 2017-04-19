package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiTax;
import solvas.service.models.InsuranceType;
import solvas.service.models.Tax;
import solvas.service.models.VehicleType;

import java.math.BigDecimal;

/**
 * Mapper between for Tax
 */
@Component
public class TaxMapper extends AbstractMapper<Tax,ApiTax> {


    /**
     * Create a mapper between Tax and ApiTax
     *
     * @param daoContext The context for Dao's
     */
    public TaxMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Tax convertToModel(ApiTax api) throws EntityNotFoundException {

        Tax tax = daoContext.getTaxDao()
                .findDistinctByVehicleTypeNameAndInsuranceTypeName(api.getVehicleType(), api.getContractType());

        // All taxes should always exist, as per the API spec. However, we support in anyway.
        // If the tax is not found, the first database request was for nothing. However, this should not happen,
        // or happen very rarely, so it's better to optimize for the case where there is already a tax.
        if (tax == null) {
            // Verify that both types exist.
            VehicleType vehicleType = daoContext.getVehicleTypeDao().findByName(api.getVehicleType());
            InsuranceType insuranceType = daoContext.getInsuranceTypeDao().findByName(api.getContractType());

            if (vehicleType == null || insuranceType == null) {
                throw new EntityNotFoundException();
            }

            tax = new Tax();
            tax.setVehicleType(vehicleType);
            tax.setInsuranceType(insuranceType);
            tax.setTax(BigDecimal.valueOf(api.getTax()));
        }

        return tax;
    }

    @Override
    public ApiTax convertToApiModel(Tax model) {
        ApiTax apiTax = new ApiTax();
        apiTax.setTax(model.getTax().doubleValue());
        return apiTax;
    }
}
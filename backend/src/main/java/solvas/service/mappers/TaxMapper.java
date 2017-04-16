package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiTax;
import solvas.rest.query.TaxFilter;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Tax;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

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
    public Tax convertToModel(ApiTax api) throws DependantEntityNotFound, EntityNotFoundException {
        Tax tax = new Tax();

        try {
            tax.setInsuranceType(daoContext.getInsuranceTypeDao()
                    .findByName(api.getContractType()).iterator().next());
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Unexistent Contracttype");
        }
        try {
            tax.setVehicleType(daoContext.getVehicleTypeDao()
                    .findByName(api.getVehicleType()).iterator().next());
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Unexistent Vehicletype");
        }
        tax.setTax(BigDecimal.valueOf(api.getTax()));
        return tax;
    }

    @Override
    public ApiTax convertToApiModel(Tax model) {
        ApiTax apiTax = new ApiTax();
        apiTax.setTax(model.getTax().doubleValue());
        return apiTax;
    }
}

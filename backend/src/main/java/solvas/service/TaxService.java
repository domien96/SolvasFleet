package solvas.service;

import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.TaxDao;
import solvas.rest.api.models.ApiTax;
import solvas.service.mappers.TaxMapper;
import solvas.service.models.Tax;

/**
 * Created by steve on 16/04/2017.
 */
@Service
public class TaxService extends AbstractService<Tax,ApiTax> {

    private TaxDao taxDao;

    /**
     * Contruct an TaxService
     *
     * @param modelDao the DAO of the model
     * @param mapper   the mapper between the apimodel and the model
     */
    public TaxService(TaxDao modelDao, TaxMapper mapper) {
        super(modelDao, mapper);
        this.taxDao = modelDao;
    }

    /**
     * Find the taxes for the tuple (vehicle type, contract type).
     *
     * @param vehicleType The type of the vehicle.
     * @param insuranceType The type of the contract.
     *
     * @return The tax for the tuple.
     *
     * @throws EntityNotFoundException If the vehicle type or contract type was not found.
     */
    public ApiTax findFor(String vehicleType, String insuranceType) throws EntityNotFoundException {

        Tax tax = taxDao.findDistinctByVehicleTypeNameAndInsuranceTypeName(vehicleType, insuranceType);

        if (tax == null) {
            throw new EntityNotFoundException();
        }

        return mapper.convertToApiModel(tax);
    }
}
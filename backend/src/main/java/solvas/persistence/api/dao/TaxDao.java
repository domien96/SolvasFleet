package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Tax;

/**
 * DAO for taxes.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface TaxDao extends Dao<Tax> {


    /**
     * Find by vehicle type and by insurance type. The combination of both is guaranteed to be unique by the
     * database.
     *
     * @param vehicleType The vehicle type.
     * @param insuranceType The insurance type.
     *
     * @return The tax.
     */
    Tax findDistinctByVehicleTypeNameAndInsuranceTypeName(String vehicleType, String insuranceType);
}
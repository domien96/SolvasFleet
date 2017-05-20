package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.*;
import solvas.persistence.api.Dao;

import java.util.Collection;
import java.util.Optional;

/**
 * DAO for Commissions.
 *
 * @author sjabasti
 */
@Repository
public interface CommissionDao extends Dao<Commission> {

    /**
     * Find the Commissions of a certain vehicle.
     *
     * @param vehicle The name.
     *
     * @return The Commissions.
     */
    Optional<Commission> findByVehicle(Vehicle vehicle);


    /**
     * Find all Commissions with a certain subfleet.
     *
     * @param fleet The fleet.
     * @param vehicleType The vehicleType.
     *
     * @return The Commissions.
     */
    Optional<Commission> findByFleetAndVehicleTypeAndVehicleIsNull(Fleet fleet, VehicleType vehicleType);

    /**
     * Find all Commissions with a certain fleet.
     *
     * @param fleet The fleet.
     *
     * @return The Commissions.
     */
    Optional<Commission> findByFleetAndVehicleIsNullAndVehicleTypeIsNull(Fleet fleet);

    /**
     * Find all Commissions with a certain company.
     *
     * @param company The company.
     *
     * @return The Commissions.
     */
    Collection<Commission> findByCompanyAndVehicleIsNullAndVehicleTypeIsNullAndFleetIsNull(Company company);

}
package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Company;
import solvas.service.models.Contract;
import solvas.service.models.FleetSubscription;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * DAO for Contract.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface ContractDao extends Dao<Contract> {

    /**
     * Find all contracts with given company
     *
     * @param company the company of which the contracts are needed
     * @return Contracts with given company
     */
    Collection<Contract> findByCompany(Company company);


    /**
     * Find all contracts for a subscription which overlaps with the period start-end
     * @param fleetSubscription
     * @param start
     * @param end
     * @return
     */
    Collection<Contract> findAllByFleetSubscriptionAndEndDateGreaterThanEqualAndStartDateLessThanEqual(FleetSubscription fleetSubscription, LocalDateTime start, LocalDateTime end);

    /**
     * Convenient short named alias for findAllByFleetSubscriptionAndEndDateGreaterThanEqualAndStartDateLessThanEqual
     * @param fleetSubscription
     * @param start
     * @param end
     * @return
     */
    default Collection<Contract> findByFleetSubscriptionAndOverlaps(FleetSubscription fleetSubscription, LocalDateTime start, LocalDateTime end) {
        return findAllByFleetSubscriptionAndEndDateGreaterThanEqualAndStartDateLessThanEqual(fleetSubscription, start, end);
    }

}
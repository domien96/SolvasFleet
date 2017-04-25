package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Contract;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Invoice;

import java.time.LocalDate;
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
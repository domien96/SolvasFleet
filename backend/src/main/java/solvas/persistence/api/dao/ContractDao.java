package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Company;
import solvas.service.models.Contract;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

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
     * Find all contracts with given fleetSubscription
     *
     * @param fleetSubscription the fleetSubscription of which the contracts are needed
     * @return Contracts with given fleetSubscription
     */
    Collection<Contract> findByFleetSubscription(FleetSubscription fleetSubscription);

    /**
     * Get all contracts for a fleet where the start date is before a given date and the end date is after a given date.
     * The before and after are strict.
     *
     * In simpler terms, this returns all active contracts for a given period. If the start and end date are the same,
     * it returns all contracts that were active on a given day.
     *
     * An example to get all active contracts on 20/04/2017, use following dates:
     * <ul>
     *     <li>Start date: 20/04/2017 23:59</li>
     *     <li>End date: 20/04/2017 00:00</li>
     * </ul>
     *
     * In the example above, contracts that ended or began on 20/04/2017 are also included. Swap the dates to
     * prevent this.
     *
     * @param fleet The fleet for which contracts are sought.
     * @param startDate The datetime before which the contract must have started.
     * @param endDate The datetime after which the contract must have ended.
     *
     * @return The contracts, or an empty collection if no contracts were found.
     */
    Collection<Contract> findByFleetSubscriptionFleetAndStartDateBeforeAndEndDateAfter(Fleet fleet, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * This method is very similar to {@link #findByFleetSubscriptionFleetAndStartDateBeforeAndEndDateAfter(Fleet, LocalDateTime, LocalDateTime)},
     * but this method searches for contracts whose start date is after the given start date.
     *
     * In other words, this returns contracts that were not active on the start date, but began after the start date.
     * Another way would be: it returns contracts that started in between the given dates.
     *
     * @param fleet The fleet for which contracts are sought.
     * @param startDate The datetime before which the contract must have started.
     * @param endDate The time before a contract must have started.
     *
     * @return The contracts, or an empty collection if no contracts were found.
     */
    Collection<Contract> findByFleetSubscriptionFleetAndStartDateAfterAndStartDateLessThanEqual(Fleet fleet, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find contracts that were created after a certain day, for a fleet and for which the start date is a certain date.
     *
     * @param fleet The fleet.
     * @param createdAt The date after which the contract must be created.
     * @param startDateBegin The start date of the contract.
     *
     * @return The contracts.
     */
    Collection<Contract> findByFleetSubscriptionFleetAndCreatedAtAfterAndStartDateBetween(Fleet fleet, LocalDateTime createdAt, LocalDateTime startDateBegin, LocalDateTime startDateEnd);

    /**
     * Find the oldest contract for a fleet.
     *
     * @param fleet The fleet to find the contract for.
     *
     * @return The contract.
     */
    Optional<Contract> findFirstByFleetSubscriptionFleetOrderByStartDateAsc(Fleet fleet);

}
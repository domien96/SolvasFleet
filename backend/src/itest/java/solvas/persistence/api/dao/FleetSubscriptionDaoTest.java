package solvas.persistence.api.dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import java.time.LocalDate;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests for custom behaviour on the fleet subscription DAO.
 *
 * @author Niko Strijbol
 * @author Karel Vandenbussche
 */
public class FleetSubscriptionDaoTest extends DaoTest {

    @Autowired
    private FleetSubscriptionDao fleetSubscriptionDao;

    /**
     * Test the custom behaviour in one method, since the second test depends on the first test.
     */
    @Test
    public void testCustomBehaviour()
    {
        // Find all subscriptions
        Vehicle vehicle = manager.find(Vehicle.class, 3);
        Collection<FleetSubscription> subscriptions = fleetSubscriptionDao.findByVehicle(vehicle);

        // Test that we have all of them
        assertThat(subscriptions, hasSize(2));

        // Make sure only one is active
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusYears(2);
        LocalDate end = now.minusYears(1);
        for(FleetSubscription subscription: subscriptions) {
            subscription.setStartDate(start);
            subscription.setEndDate(end);
        }

        // Test no active
        assertFalse(fleetSubscriptionDao.activeForVehicle(vehicle).isPresent());

        // Set the first one to active
        FleetSubscription subscription = subscriptions.iterator().next();
        subscription.setEndDate(now.plusYears(1));

        assertTrue(fleetSubscriptionDao.activeForVehicle(vehicle).isPresent());
    }
}
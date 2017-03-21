package solvas.persitence.api.dao;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import solvas.Application;
import solvas.models.FleetSubscription;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FleetSubscriptionDao;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static solvas.rest.utils.IteratorUtils.toList;

/**
 * Integration tests of FleetSubscriptionDao
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {Application.class, TestConfig.class})
@Transactional
public class FleetSubscriptionDaoTest {

    @Autowired
    private FleetSubscriptionDao fleetSubscriptionDao;

    /**
     * Test: adding a FleetSubscription to the database
     */
    @Test
    public void addFleetSubscription()
    {
        FleetSubscription newSub = random(FleetSubscription.class,"id");
        newSub.getVehicle().setId(45);
        newSub.getSubFleet().setId(88);
        fleetSubscriptionDao.save(newSub);
        assertThat(toList(fleetSubscriptionDao.findAll()),hasSize(101));
        assertFleetSubscriptions(newSub,fleetSubscriptionDao.find(newSub.getId()));
    }

    /**
     * Test: delete a FleetSubscription on the database
     */
    @Ignore //can't destroy: subfleet reference fleet
    @Test(expected = EntityNotFoundException.class)
    public void destroyFleet()
    {

    }

    /**
     * Test: update a FleetSubscription on the database
     */
    @Test
    public void updateFleetSubscription()
    {
        FleetSubscription newSub = random(FleetSubscription.class,"id");
        FleetSubscription old = fleetSubscriptionDao.find(11);
        newSub.setId(11);
        fleetSubscriptionDao.save(newSub);
        assertFleetSubscriptions(newSub,fleetSubscriptionDao.find(11));
    }

    /**
     * Test: find a specific FleetSubscription
     */
    @Test
    public void findFleetSubscriptionById()
    {
        assertThat(fleetSubscriptionDao.find(10),notNullValue());
    }

    /**
     * Test: get all FleetSubscription from the database
     */
    @Test
    public void findFleetSubscriptions()
    {
        assertThat(toList(fleetSubscriptionDao.findAll()),hasSize(100));
    }

    private void assertFleetSubscriptions(FleetSubscription expected, FleetSubscription actual)
    {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getSubFleet().getId(),is(equalTo(expected.getSubFleet().getId())));
        assertThat(actual.getVehicle().getId(),is(equalTo(expected.getVehicle().getId())));
    }
}



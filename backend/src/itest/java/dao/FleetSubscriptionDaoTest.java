package dao;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.FleetSubscription;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.persistence.hibernate.HibernateConfig;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Integration tests of FleetSubscriptionDao
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HibernateConfig.class,HibernateTestConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class FleetSubscriptionDaoTest {

    @Autowired
    private FleetSubscriptionDao fleetSubscriptionDao;

    /**
     * Test: adding a FleetSubscription to the database
     */
    @Test
    public void addFleetSubscription() throws EntityNotFoundException {
        FleetSubscription newSub = random(FleetSubscription.class,"id");
        newSub.getVehicle().setId(45);
        newSub.getSubFleet().setId(88);
        fleetSubscriptionDao.create(newSub);
        assertThat(fleetSubscriptionDao.findAll(),hasSize(101));
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
    public void updateFleetSubscription() throws EntityNotFoundException {
        FleetSubscription newSub = random(FleetSubscription.class,"id");
        FleetSubscription old = fleetSubscriptionDao.find(11);
        newSub.setId(11);
        fleetSubscriptionDao.update(newSub);
        assertFleetSubscriptions(newSub,fleetSubscriptionDao.find(11));
    }

    /**
     * Test: find a specific FleetSubscription
     */
    @Test
    public void findFleetSubscriptionById() throws EntityNotFoundException {
        assertThat(fleetSubscriptionDao.find(10),notNullValue());
    }

    /**
     * Test: get all FleetSubscription from the database
     */
    @Test
    public void findFleetSubscriptions()
    {
        assertThat(fleetSubscriptionDao.findAll(),hasSize(100));
    }

    private void assertFleetSubscriptions(FleetSubscription expected, FleetSubscription actual)
    {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getSubFleet().getId(),is(equalTo(expected.getSubFleet().getId())));
        assertThat(actual.getVehicle().getId(),is(equalTo(expected.getVehicle().getId())));
    }
}



package dao;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Fleet;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FleetDao;
import solvas.persistence.hibernate.HibernateConfig;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static solvas.rest.utils.IteratorUtils.toList;

/**
 * Integration tests of FleetDao
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HibernateConfig.class,HibernateTestConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class FleetDaoTest {

    @Autowired
    private FleetDao fleetDao;


    private Fleet fleet;

    @Before
    public void setUp()
    {
        fleet=random(Fleet.class,"id");
    }

    /**
     * Test: adding a fleet to the database
     */
    @Test
    public void addFleet()
    {
        Fleet newFleet = random(Fleet.class);
        newFleet.getCompany().setId(23);
        fleetDao.save(newFleet);
        assertThat(toList(fleetDao.findAll()),hasSize(101));
        assertFleets(newFleet,fleetDao.find(newFleet.getId()));
    }

    /**
     * Test: delete a fleet on the database
     */
    @Ignore //can't destroy: subfleet reference fleet
    @Test(expected = EntityNotFoundException.class)
    public void destroyFleet()
    {
        fleet=fleetDao.find(4);
        fleetDao.destroy(fleet);
        assertThat(toList(fleetDao.findAll()),hasSize(99));
        fleetDao.find(fleet.getId());
    }

    /**
     * Test: update a fleet on the database
     */
    @Test
    public void updateFleet()
    {
        Fleet old = fleetDao.find(9);
        Fleet updated = random(Fleet.class);
        updated.setId(9);
        updated.getCompany().setId(old.getCompany().getId());
        fleetDao.save(updated);
        assertFleets(updated,fleetDao.find(9));
    }

    /**
     * Test: find a specific role
     */
    @Test
    public void findFleetById()
    {
        assertThat(fleetDao.find(10),notNullValue());
    }

    /**
     * Test: get all roles from the database
     */
    @Test
    public void findFleets()
    {
        assertThat(toList(fleetDao.findAll()),hasSize(100));
    }

    private void assertFleets(Fleet expected, Fleet actual)
    {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getName(),is(equalTo(expected.getName())));
        assertThat(actual.getCompany().getId(),is(equalTo(expected.getCompany().getId())));
    }
}


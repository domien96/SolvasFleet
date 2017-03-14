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
import solvas.models.SubFleet;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.HibernateConfig;
import solvas.persistence.subFleet.SubFleetDao;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Integration tests of SubFleetDao
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HibernateConfig.class,HibernateTestConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class SubFleetDaoTest {

    @Autowired
    private SubFleetDao subFleetDao;


    /**
     * Test: adding a subfleet to the database
     */
    @Test
    public void addSubFleet()
    {
        SubFleet newSubFleet = random(SubFleet.class);
        newSubFleet.getFleet().setId(10);
        newSubFleet.getVehicleType().setId(2);
        subFleetDao.create(newSubFleet);
        assertThat(subFleetDao.findAll(),hasSize(101));
        assertSubFleets(newSubFleet,subFleetDao.find(newSubFleet.getId()));
    }

    /**
     * Test: delete a fleet on the database
     */
    @Ignore //can't destroy: subfleet references fleet
    @Test(expected = EntityNotFoundException.class)
    public void destroySubFleet()
    {

    }

    /**
     * Test: update a subfleet on the database
     */
    @Test
    public void updateSubFleet()
    {
        SubFleet old = subFleetDao.find(44);
        SubFleet updated = random(SubFleet.class);
        updated.setId(44);
        updated.getFleet().setId(old.getFleet().getId());
        updated.getVehicleType().setId(old.getVehicleType().getId());
        subFleetDao.update(updated);
        assertSubFleets(updated,subFleetDao.find(44));
    }

    /**
     * Test: find a specific subfleet
     */
    @Test
    public void findFleetById()
    {
        assertThat(subFleetDao.find(10),notNullValue());
    }

    /**
     * Test: get all subfleets from the database
     */
    @Test
    public void findFleets()
    {
        assertThat(subFleetDao.findAll(),hasSize(100));
    }

    private void assertSubFleets(SubFleet expected, SubFleet actual)
    {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getVehicleType().getId(),is(equalTo(expected.getVehicleType().getId())));
        assertThat(actual.getFleet().getId(),is(equalTo(expected.getFleet().getId())));
    }
}



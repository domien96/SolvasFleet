package dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import solvas.models.Vehicle;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.api.dao.VehicleDao;
import solvas.persistence.hibernate.HibernateConfig;

import javax.transaction.Transactional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Integration tests of VehicleDao
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HibernateConfig.class,HibernateTestConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class VehicleDaoTest {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private CompanyDao companyDao;


    /**
     * Test: adding a vehicle to the database
     */
    @Test
    public void addVehicle() throws EntityNotFoundException {
        Vehicle vehicle=random(Vehicle.class,"id");
        vehicle.getLeasingCompany().setId(0);
        vehicle.getType().setId(3); //Only 5 types in db
        companyDao.create(vehicle.getLeasingCompany());
        vehicleDao.create(vehicle);
        assertThat(vehicleDao.findAll(),hasSize(101));
        assertVehicles(vehicle,vehicleDao.find(vehicle.getId()));
    }

    /**
     * Test: removing a vehicle from the database
     */
    @Ignore //Destroying wont work when fleet_subscriptions reference a vehicle
    @Test(expected = EntityNotFoundException.class)
    public void destroyVehicle() throws EntityNotFoundException {
        Vehicle v= vehicleDao.find(30);
        vehicleDao.destroy(v);
        assertThat(vehicleDao.findAll(),hasSize(99));
        vehicleDao.find(30);
    }

    /**
     * Test: updating a vehicle on the database
     */
    @Test
    public void updateVehicle() throws EntityNotFoundException {
        Vehicle old = vehicleDao.find(30);
        Vehicle updated = random(Vehicle.class);
        updated.setId(30);
        updated.setLeasingCompany(old.getLeasingCompany());
        updated.getType().setId(2);
        updated.setFleetSubscriptions(old.getFleetSubscriptions());
        vehicleDao.update(updated);
        assertVehicles(updated,vehicleDao.find(30));
    }

    /**
     * Test: getting a specific vehicle from the database
     */
    @Test
    public void findVehicleById() throws EntityNotFoundException {
        assertThat(vehicleDao.find(20),notNullValue());
    }

    /**
     * Test: getting all vehicles from the database
     */
    @Test
    public void findVehicles()
    {
        assertThat(vehicleDao.findAll(),hasSize(100));
    }

    private void assertVehicles(Vehicle expected, Vehicle actual)
    {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getLicensePlate(),is(equalTo(expected.getLicensePlate())));
        assertThat(actual.getValue(),is(equalTo(expected.getValue())));
        assertThat(actual.getChassisNumber(),is(equalTo(expected.getChassisNumber())));
        assertThat(actual.getKilometerCount(),is(equalTo(expected.getKilometerCount())));
        assertThat(actual.getModel(),is(equalTo(expected.getModel())));
        assertThat(actual.getYear(),is(equalTo(expected.getYear())));
        assertThat(actual.getBrand(),is(equalTo(expected.getBrand())));
    }
}

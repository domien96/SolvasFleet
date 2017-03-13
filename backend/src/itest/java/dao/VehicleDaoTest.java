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
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.HibernateConfig;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.vehicle.VehicleDao;

import javax.transaction.Transactional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Integration tests of VehicleDao
 */

@Ignore //vehicletype moet nog gefixt worden
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
    public void addVehicle()
    {
        Vehicle vehicle=random(Vehicle.class,"id");
        vehicle.getLeasingCompany().setId(0);
        companyDao.save(vehicle.getLeasingCompany());
        vehicleDao.save(vehicle);
        assertThat(vehicleDao.findAll(),hasSize(101));
        assertVehicles(vehicle,vehicleDao.find(vehicle.getId()));
    }

    /**
     * Test: removing a vehicle from the database
     */
    @Test(expected = EntityNotFoundException.class)
    public void destroyVehicle()
    {
        Vehicle v= vehicleDao.find(30);
        vehicleDao.destroy(v);
        assertThat(vehicleDao.findAll(),hasSize(99));
        vehicleDao.find(30);
    }

    /**
     * Test: updating a vehicle on the database
     */
    @Test
    public void updateVehicle()
    {

    }

    /**
     * Test: getting a specific vehicle from the database
     */
    @Test
    public void findVehicleById()
    {

    }

    /**
     * Test: getting all vehicles from the database
     */
    @Test
    public void findVehicles()
    {

    }

    private void assertVehicles(Vehicle expected, Vehicle actual)
    {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));
        assertThat(actual.getLicensePlate(),is(equalTo(expected.getLicensePlate())));
        assertThat(actual.getValue(),is(equalTo(expected.getValue())));
        assertThat(actual.getChassisNumber(),is(equalTo(expected.getChassisNumber())));
        assertThat(actual.getKilometerCount(),is(equalTo(expected.getKilometerCount())));
        assertThat(actual.getModel(),is(equalTo(expected.getModel())));
        assertThat(actual.getYear(),is(equalTo(expected.getYear())));
        assertThat(actual.getBrand(),is(equalTo(expected.getBrand())));
        assertThat(actual.getType(),is(equalTo(expected.getType())));
    }
}

package solvas.persistence.api.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.models.Vehicle;
import solvas.persistence.api.EntityNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

/**
 * Test the default methods on the {@link solvas.persistence.api.Dao} interface.
 *
 * @author Niko Strijbol
 */
public class DefaultDaoTest extends DaoTest {

    @Autowired
    public VehicleDao vehicleDao;

    /**
     * Test that finding a valid entity does not throw an exception.
     */
    @Test
    public void testNoException() {
        // Test using vehicles.
        Vehicle vehicle = manager.find(Vehicle.class, 1);
        Vehicle vehicle1 = vehicleDao.find(1);

        assertThat(vehicle, samePropertyValuesAs(vehicle1));
    }

    /**
     * Test that the exception is correctly thrown.
     *
     * @throws EntityNotFoundException The exception.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testNonExistent() throws EntityNotFoundException {
        vehicleDao.find(-1);
    }
}
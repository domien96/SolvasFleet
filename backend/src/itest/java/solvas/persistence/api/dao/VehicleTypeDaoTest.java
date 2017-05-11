package solvas.persistence.api.dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for custom behaviour on the vehicle type DAO.
 *
 * @author Niko Strijbol
 */
public class VehicleTypeDaoTest extends DaoTest {

    @Autowired
    private VehicleTypeDao vehicleTypeDao;


    /**
     * Test finding vehicle types by name.
     */
    @Test
    public void testTypesExists() {
        String[] types = new String[]{"PersonalVehicle", "SemiHeavyTruck", "Truck", "Truck+12", "Van"};
        Arrays.stream(types).forEach(type-> assertNotNull(vehicleTypeDao.findByName(type)));
    }
}
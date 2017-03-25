package solvas.persistence.api.dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

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
    public void testFindByFleet() {
        assertThat(vehicleTypeDao.findByName("Persoonswagen"), hasSize(1));
    }
}
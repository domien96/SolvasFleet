package solvas.persistence.api.dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.service.models.Fleet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Integration tests for custom behaviour on the fleet subscription DAO.
 *
 * @author Niko Strijbol
 * @author Karel Vandenbussche
 */
public class SubFleetDaoTest extends DaoTest {

    @Autowired
    private SubFleetDao subFleetDao;

    /**
     * Test finding sub fleets by fleet.
     */
    @Test
    public void testFindByFleet() {
        Fleet fleet = manager.find(Fleet.class, 22);
        assertThat(subFleetDao.findByFleet(fleet), hasSize(1));
    }
}



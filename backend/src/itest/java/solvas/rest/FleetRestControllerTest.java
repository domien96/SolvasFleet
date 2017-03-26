package solvas.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import solvas.models.Fleet;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.FleetDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.FleetMapper;
import solvas.rest.api.models.ApiFleet;
import solvas.rest.controller.FleetRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

/**
 * Integration tests of the FleetRestController. It checks that the controller calls the correct
 * methods and returns the correct value.
 *
 * @author Niko Strijbol
 */
@RunWith(SpringRunner.class)
@WebMvcTest(FleetRestController.class)
public class FleetRestControllerTest extends AbstractControllerTest<Fleet, ApiFleet> {

    @MockBean
    private FleetDao mockFleetDao;

    @MockBean
    private FleetMapper mockFleetMapper;

    private ApiFleet user;

    /**
     * Setup of mockMVC
     * currently provides one random user object and its json representation
     */
    @Before
    public void setUp() throws Exception {
        user = random(ApiFleet.class);
        user.setId(500);
    }

    @Override
    protected Dao<Fleet> getMockDao() {
        return mockFleetDao;
    }

    @Override
    protected AbstractMapper<Fleet, ApiFleet> getMockMapper() {
        return mockFleetMapper;
    }

    @Override
    protected ApiFleet getValidModel() {
        return user;
    }

    @Override
    protected String getBaseUrl() {
        return "/fleets/";
    }

    @Override
    protected Class<ApiFleet> getApiClass() {
        return ApiFleet.class;
    }

    @Override
    protected Class<Fleet> getModelClass() {
        return Fleet.class;
    }
}
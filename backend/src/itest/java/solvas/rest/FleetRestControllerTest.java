package solvas.rest;

import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import solvas.rest.api.models.ApiFleet;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.FleetRestController;
import solvas.service.AbstractService;
import solvas.service.FleetService;
import solvas.service.models.Fleet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Integration tests of fleetrestcontroller
 */
public class FleetRestControllerTest extends AbstractRestControllerTest<Fleet,ApiFleet> {
    @Mock
    private FleetService service;

    /**
     * Constructor for specific FleetController tests
     */
    public FleetRestControllerTest() {
        super(ApiFleet.class);
    }

    @Override
    AbstractRestController getController() {
        return new FleetRestController(service);
    }

    @Override
    protected AbstractService<Fleet, ApiFleet> getService() {
        return service;
    }

    @Override
    protected String getBaseUrl() {
        return RestTestFixtures.FLEET_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.FLEET_ID_URL;
    }


    /**
     * Match jsonmodel with ApiCompany
     * @param res the ResultAction that mockMvc provides.
     * @param model the fleet we want to compare with the json result
     */
    @Override
    public void matchJsonModel(ResultActions res, ApiFleet model) throws Exception {
        res.andExpect(jsonPath("id").value(model.getId()))
                .andExpect(jsonPath("company").value(model.getCompany()))
                .andExpect(jsonPath("name").value(model.getName()));

    }
}

package solvas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import solvas.models.Vehicle;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.VehicleAbstractMapper;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.controller.VehicleRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

/**
 * Integration tests of the VehicleRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
@RunWith(SpringRunner.class)
@WebMvcTest(VehicleRestController.class)
public class VehicleRestControllerTest extends AbstractControllerTest<Vehicle, ApiVehicle> {

    @MockBean
    private VehicleDao vehicleDaoMock;

    @MockBean
    private VehicleAbstractMapper vehicleAbstractMapperMock;

    private ApiVehicle vehicle;

    /**
     * Setup of mockMVC
     * currently provides one random vehicle object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
        vehicle = random(ApiVehicle.class);
        vehicle.setId(500);
        vehicle.setVin("WDBRN40J75A645754");
        vehicle.setYear(2017);
        vehicle.setMileage(10);
        vehicle.setValue(6565);
    }

    @Override
    protected Dao<Vehicle> getMockDao() {
        return vehicleDaoMock;
    }

    @Override
    protected AbstractMapper<Vehicle, ApiVehicle> getMockMapper() {
        return vehicleAbstractMapperMock;
    }

    @Override
    protected ApiVehicle getValidModel() {
        return vehicle;
    }

    @Override
    protected String getBaseUrl() {
        return "/vehicles/";
    }

    @Override
    protected Class<ApiVehicle> getApiClass() {
        return ApiVehicle.class;
    }

    @Override
    protected Class<Vehicle> getModelClass() {
        return Vehicle.class;
    }
}
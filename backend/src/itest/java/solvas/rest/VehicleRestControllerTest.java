package solvas.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Vehicle;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.VehicleRestController;
import solvas.rest.service.AbstractService;
import solvas.rest.service.VehicleService;
import solvas.rest.utils.JsonListWrapper;

import static io.github.benas.randombeans.api.EnhancedRandom.randomSetOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the VehicleRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
@RunWith(MockitoJUnitRunner.class)
public class VehicleRestControllerTest extends AbstractRestControllerTest<Vehicle,ApiVehicle>{
    @Mock
    private VehicleService vehicleService;

    public VehicleRestControllerTest() {
        super(ApiVehicle.class);
    }



    /**
     * Method to check if json has the correct attributes
     */
    public void matchJsonModel(ResultActions res, ApiVehicle vehicle) throws Exception {

        res.andExpect(jsonPath("id").value(vehicle.getId()))
                .andExpect(jsonPath("url").value(vehicle.getUrl()))
                .andExpect(jsonPath("licensePlate").value(vehicle.getLicensePlate()))
                .andExpect(jsonPath("value").value(vehicle.getValue()))
                .andExpect(jsonPath("mileage").value(vehicle.getMileage()))
                .andExpect(jsonPath("model").value(vehicle.getModel()))
                .andExpect(jsonPath("brand").value(vehicle.getBrand()))
                .andExpect(jsonPath("leasingCompany").value(vehicle.getLeasingCompany()))
                .andExpect(jsonPath("type").value(vehicle.getType()))
                .andExpect(jsonPath("year").value(vehicle.getYear()));

    }

    /**
     * @return the vehicle rest controller
     */
    @Override
    AbstractRestController getController() {
        return new VehicleRestController(vehicleService);
    }

    @Override
    ApiVehicle getTestModel()
    {
        ApiVehicle vehicle = super.getTestModel();
        vehicle.setValue(1500);
        vehicle.setYear(1990);
        vehicle.setMileage(10000);
        vehicle.setVin("5NPEB4AC8EH893920");
        return vehicle;
    }

    @Override
    protected AbstractService<Vehicle, ApiVehicle> getService() {
        return vehicleService;
    }

    @Override
    protected String getBaseUrl() {
        return "/vehicles";
    }

    @Override
    public String getIdUrl() {
        return "/vehicles/10";
    }
}

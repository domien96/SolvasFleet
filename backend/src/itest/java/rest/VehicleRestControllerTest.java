package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import solvas.models.validators.VehicleValidator;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.VehicleRestController;
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
public class VehicleRestControllerTest extends AbstractRestControllerTest<ApiVehicle>{
    @Mock
    private VehicleService vehicleService;
    @Mock
    private VehicleValidator vehicleValidator;

    public VehicleRestControllerTest() {
        super(ApiVehicle.class);
    }


    /**
     * Setup of mockMVC
     * currently provides one random vehicle object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
    }

    /**
     * Test: the response of a get request for a user that doesn't exist on the db
     */
    @Test
    public void getVehicleByIdNoError() throws Exception
    {
        when(vehicleService.getById(anyInt())).thenReturn(getTestModel());
        ResultActions resultActions=getMockMvc()
                .perform(get(TestFixtures.vehicleIdUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        matchVehicleJson(resultActions,getTestModel());
    }

    /**
     * Test: the response of a get request for a vehicle that doesn't exist on the db
     */
    @Test
    public void getVehicleByIdNotFound() throws Exception
    {
        when(vehicleService.getById(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(get(TestFixtures.vehicleIdUrl))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for all the vehicles
     */
    @Test
    public void getVehiclesNoError() throws Exception
    {
        when(vehicleService.findAndWrap(any(),any())).thenReturn(new JsonListWrapper<>(randomSetOf(10,ApiVehicle.class)));
        getMockMvc()
                .perform(get(TestFixtures.vehicleBaseUrl))
                .andExpect(status().isOk());
    }

    /**
     * Test: the response of a put request for a new vehicle that doesn't exist on the db
     */
    @Test
    public void postVehicleNoError() throws Exception {
        when(vehicleService.create(any())).thenReturn(getTestModel());
        ResultActions resultActions=getMockMvc()
                .perform(post(TestFixtures.vehicleBaseUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk());

        matchVehicleJson(resultActions,getTestModel());
    }

    /**
     * Test: the response of a post request for a vehicle that already exists  (error)
     */
    @Ignore //case bespreken wanneer een vehicle al bestaat
    @Test
    public void postVehicleAlreadyExists() throws Exception {

    }

    @Test
    public void putVehicleNoError() throws Exception {
        when(vehicleService.update(anyInt(),any())).thenReturn(getTestModel());
        ResultActions resultActions=getMockMvc()
                .perform(put(TestFixtures.vehicleIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk());
        matchVehicleJson(resultActions,getTestModel());
    }

    @Ignore
    @Test
    public void putVehicleNotFound() throws Exception {
        when(vehicleService.update(anyInt(),any())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(put(TestFixtures.vehicleIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void destroyVehicleNoError() throws Exception
    {
        getMockMvc()
                .perform(delete(TestFixtures.vehicleIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk());
        verify(vehicleService,times(1)).destroy(anyInt());
    }

    @Test
    public void destroyVehicleNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(vehicleService).destroy(anyInt());
        getMockMvc()
                .perform(delete(TestFixtures.vehicleIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }




    /**
     * Method that checks the result of the json string that is contained in the HTTP request
     * @param res from MockMVC object
     * @param vehicle vehicle object that should be equal to the object in json representation
     * @throws Exception when mockMVC fails to perform the action or jsonPath fails to retrieve the attribute
     */
    public void matchVehicleJson(ResultActions res, ApiVehicle vehicle) throws Exception {

        res.andExpect(jsonPath("id").value(vehicle.getId()))
                .andExpect(jsonPath("url").value(vehicle.getUrl()))
                .andExpect(jsonPath("licensePlate").value(vehicle.getLicensePlate()))
                .andExpect(jsonPath("value").value(vehicle.getValue()))
                //.andExpect(jsonPath("chassisNumber").value(vehicle.get()))
                .andExpect(jsonPath("mileage").value(vehicle.getMileage()))
                .andExpect(jsonPath("model").value(vehicle.getModel()))
                .andExpect(jsonPath("brand").value(vehicle.getBrand()))
                .andExpect(jsonPath("leasingCompany").value(vehicle.getLeasingCompany()))
                .andExpect(jsonPath("type").value(vehicle.getType()))
                .andExpect(jsonPath("year").value(vehicle.getYear()));

    }

    @Override
    AbstractRestController getController() {
        return new VehicleRestController(vehicleService, vehicleValidator);
    }
}

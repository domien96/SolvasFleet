package solvas.rest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.Validator;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.VehicleRestController;
import solvas.service.AbstractService;
import solvas.service.VehicleService;
import solvas.service.models.Vehicle;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the VehicleRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
public class VehicleRestControllerTest extends AbstractRestControllerTest<Vehicle,ApiVehicle>{
    @Mock
    private VehicleService vehicleService;

    @Mock
    private Validator validator;

    private final static int VEHICLE_VALUE = 1500;
    private final static int VEHICLE_MILEAGE = 10000;
    private final static int VEHICLE_YEAR = 1990;


    /**
     * Constructor for specific VehicleController tests
     */
    public VehicleRestControllerTest() {
        super(ApiVehicle.class);
    }

    /**
     * Setup to make sure the vehicleservice returns some types
     */
    @Before
    public void setUp(){
        when(vehicleService.findAllVehicleTypes()).thenReturn(Arrays.asList("a","b"));
    }

    /**
     * @return the vehicle rest controller
     */
    @Override
    AbstractRestController getController() {
        return new VehicleRestController(vehicleService, validator);
    }

    @Override
    ApiVehicle getTestModel()
    {
        ApiVehicle vehicle = super.getTestModel();
        vehicle.setValue(VEHICLE_VALUE);
        vehicle.setYear(LocalDateTime.of(VEHICLE_YEAR,1,1,0,0));
        vehicle.setMileage(VEHICLE_MILEAGE);
        vehicle.setVin("5NPEB4AC8EH893920");
        return vehicle;
    }

    @Override
    protected AbstractService<Vehicle, ApiVehicle> getService() {
        return vehicleService;
    }

    @Override
    protected String getBaseUrl() {
        return RestTestFixtures.VEHICLE_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.VEHICLE_ID_URL;
    }

    /**
     * Test listing all vehicletypes
     */
    @Test
    public void listAllTypes() throws Exception {
        List<String> types = Arrays.asList("a","b");
        when(vehicleService.findAllVehicleTypes()).thenReturn(types);
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_TYPES_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("data", Matchers.hasToString(getObjectMapper().writeValueAsString(types))));
    }

    /**
     * Test listing all the vehicles of a certain fleet
     */
    @Test
    public void listAllVehiclesAtFleet() throws Exception {
        when(vehicleService.findAll(any(),any())).thenReturn(new PageImpl(getTestModelList()));
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_AT_FLEET_ROOT_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("data",Matchers.hasToString(getObjectMapper().writeValueAsString(getTestModelList()))));
    }

    /**
     * Test receiving a vehicle of a fleet
     */
    @Test
    public void getVehicleAtFleet() throws Exception {
        when(vehicleService.getById(anyInt())).thenReturn(getTestModel());
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_AT_FLEET_ID_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getTestJson()));
    }

    /**
     * Test: getting NotFound when EntitityNotFoundException has been thrown
     */
    @Test
    public void getVehicleAtFleetNotFound() throws Exception {
        when(vehicleService.getById(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_AT_FLEET_ID_URL))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: getting the pdf greencard (can't check content)
     */
    @Test
    public void getGreenCard() throws Exception {
        when(vehicleService.getById(anyInt())).thenReturn(getTestModel());
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_GREENCARD_URL))
                .andExpect(status().isOk());
    }

    /**
     * Test: result when entity is missing for the greencard
     */
    @Test
    public void getGreenCardNotFound() throws Exception {
        when(vehicleService.getById(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_GREENCARD_URL))
                .andExpect(status().isNotFound());
    }

    @Test
    public void uploadCorrectCSV() throws Exception {
        FileInputStream fis = new FileInputStream("src/itest/java/solvas/rest/vehicles_correct.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);

        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        getMockMvc().perform(MockMvcRequestBuilders.fileUpload(RestTestFixtures.VEHICLE_UPLOAD_URL)
                .file("file", multipartFile.getBytes())
                .param("path", "dummy")) // just a dummy param to be able to call andExpect
                .andExpect(status().isNoContent());
    }
}

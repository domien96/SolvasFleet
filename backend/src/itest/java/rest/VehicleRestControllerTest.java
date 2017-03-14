package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Vehicle;
import solvas.models.validators.VehicleValidator;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.mappers.VehicleAbstractMapper;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.controller.VehicleRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomCollectionOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the VehicleRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
public class VehicleRestControllerTest {
    @Mock
    private VehicleDao vehicleDaoMock;

    @Mock
    private VehicleValidator validatorMock;

    @Mock
    private VehicleAbstractMapper vehicleAbstractMapperMock;

    @Mock
    private DaoContext daoContextMock;

    private MockMvc mockMvc;

    private ArgumentCaptor<Vehicle> captor = ArgumentCaptor.forClass(Vehicle.class);

    private ApiVehicle vehicle;
    private String json;

    /**
     * Setup of mockMVC
     * currently provides one random vehicle object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(daoContextMock.getVehicleDao()).thenReturn(vehicleDaoMock);
        VehicleRestController vehicleRestController=new VehicleRestController(daoContextMock,vehicleAbstractMapperMock,validatorMock);
        mockMvc= MockMvcBuilders.standaloneSetup(vehicleRestController).build();

        vehicle = random(ApiVehicle.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        json=mapper.writeValueAsString(vehicle);
    }

    /**
     * Test: the response of a get request for a user that doesn't exist on the db
     */
    @Test
    public void getVehicleByIdNoError() throws Exception
    {
        when(vehicleAbstractMapperMock.convertToApiModel(any())).thenReturn(vehicle);
        ResultActions resultActions=
                mockMvc.perform(get("/vehicles/33"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        matchVehicleJson(resultActions,vehicle);
    }

    /**
     * Test: the response of a get request for a vehicle that doesn't exist on the db
     */
    @Test
    public void getVehicleByIdNotFound() throws Exception
    {
        when(vehicleAbstractMapperMock.convertToApiModel(any())).thenReturn(vehicle);
        when(vehicleDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for all the vehicles
     */
    @Test
    public void getVehiclesNoError() throws Exception
    {
        when(vehicleDaoMock.findAll()).thenReturn(randomCollectionOf(10,Vehicle.class));
        when(vehicleAbstractMapperMock.convertToApiModel(any())).thenReturn(random(ApiVehicle.class));
        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk());
    }

    /**
     * Test: the response of a put request for a new vehicle that doesn't exist on the db
     */
    @Test
    public void postVehicleNoError() throws Exception {
        when(vehicleAbstractMapperMock.convertToApiModel(any())).thenReturn(vehicle);
        when(vehicleAbstractMapperMock.convertToModel(any())).thenReturn(random(Vehicle.class));

        ResultActions resultActions=
                mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());

        matchVehicleJson(resultActions,vehicle);

        verify(vehicleDaoMock,times(1)).create(captor.capture());
    //    matcher.performAsserts(vehicle,captor.getValue());
    }

    /**
     * Test: the response of a post request for a vehicle that already exists  (error)
     */
    @Ignore //case bespreken wanneer een vehicle al bestaat
    @Test
    public void postVehicleAlreadyExists() throws Exception {
        mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void putVehicleNoError() throws Exception {
        when(vehicleAbstractMapperMock.convertToApiModel(any())).thenReturn(vehicle);
        when(vehicleAbstractMapperMock.convertToModel(any())).thenReturn(random(Vehicle.class));
        ResultActions resultActions=
                mockMvc.perform(put("/vehicles/9").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());
        matchVehicleJson(resultActions,vehicle);

        verify(vehicleDaoMock,times(1)).update(captor.capture());
    }

    @Test
    public void putVehicleNotFound() throws Exception {
        when(vehicleAbstractMapperMock.convertToApiModel(any())).thenReturn(vehicle);
        when(vehicleAbstractMapperMock.convertToModel(any())).thenReturn(random(Vehicle.class));
        when(vehicleDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/vehicles/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void destroyVehicleNoError() throws Exception
    {
        when(vehicleDaoMock.destroy(any())).thenReturn(random(Vehicle.class));
        ResultActions res =mockMvc.perform(delete("/vehicles/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());
        verify(vehicleDaoMock,times(1)).destroy(captor.capture());
    }

    @Test
    public void destroyVehicleNotFound() throws Exception {
        when(vehicleDaoMock.destroy(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/vehicles/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
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
}

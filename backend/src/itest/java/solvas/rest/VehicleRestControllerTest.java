package solvas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Vehicle;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.mappers.VehicleAbstractMapper;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.controller.VehicleRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
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
@RunWith(SpringRunner.class)
@WebMvcTest(VehicleRestController.class)
public class VehicleRestControllerTest {
    @MockBean
    private VehicleDao vehicleDaoMock;

    @MockBean
    private VehicleAbstractMapper vehicleAbstractMapperMock;

    @Autowired
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
        vehicle = random(ApiVehicle.class);
        vehicle.setVin("WDBRN40J75A645754");
        vehicle.setYear(2017);
        vehicle.setMileage(10);
        vehicle.setValue(6565);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);;
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
        // Empty page
        Page<Vehicle> emptyPage = new PageBuilder<Vehicle>()
                .totalElements(0)
                .build();
        when(vehicleDaoMock.findAll(any(), any(Pageable.class))).thenReturn(emptyPage);
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

        verify(vehicleDaoMock,times(1)).save(captor.capture());
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

    @Ignore
    @Test
    public void putVehicleNotFound() throws Exception {
        when(vehicleAbstractMapperMock.convertToApiModel(any())).thenReturn(vehicle);
        when(vehicleAbstractMapperMock.convertToModel(any())).thenReturn(random(Vehicle.class));
        when(vehicleDaoMock.save(any(Vehicle.class))).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/vehicles/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void destroyVehicleNoError() throws Exception
    {
        when(vehicleDaoMock.destroy(any())).thenReturn(random(Vehicle.class));
        mockMvc.perform(delete("/vehicles/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNoContent());
        verify(vehicleDaoMock,times(1)).destroy(anyInt());
    }

    @Test
    public void destroyVehicleNotFound() throws Exception {
        when(vehicleDaoMock.destroy(anyInt())).thenThrow(new EntityNotFoundException());
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

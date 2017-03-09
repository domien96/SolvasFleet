package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matchers.VehicleMatcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Vehicle;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.vehicle.VehicleDao;
import solvas.rest.controller.VehicleRestController;

import java.util.Collections;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
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

    @InjectMocks
    private VehicleRestController vehicleRestController;
    private MockMvc mockMvc;

    private ArgumentCaptor<Vehicle> captor = ArgumentCaptor.forClass(Vehicle.class);
    private VehicleMatcher matcher=new VehicleMatcher();

    private Vehicle vehicle;
    private String json;

    /**
     * Setup of mockMVC
     * currently provides one random vehicle object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(vehicleRestController).build();

        vehicle = random(Vehicle.class);
        json=new ObjectMapper().writeValueAsString(vehicle);
    }

    /**
     * Test: the response of a get request for a user that doesn't exist on the db
     */
    @Test
    public void getVehicleByIdNoError() throws Exception
    {
        when(vehicleDaoMock.find(anyInt())).thenReturn(vehicle);
        ResultActions resultActions=
                mockMvc.perform(get("/vehicles/33"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        matcher.jsonMatcher(resultActions,vehicle);
    }

    /**
     * Test: the response of a get request for a vehicle that doesn't exist on the db
     */
    @Test
    public void getVehicleByIdNotFound() throws Exception
    {
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
        when(vehicleDaoMock.findAll()).thenReturn(Collections.singletonList(vehicle));
        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk());
    }

    /**
     * Test: the response of a put request for a new vehicle that doesn't exist on the db
     */
    @Test
    public void postVehicleNoError() throws Exception {
        when(vehicleDaoMock.save(any())).thenReturn(vehicle);
        ResultActions resultActions=
                mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());

        matcher.jsonMatcher(resultActions,vehicle);

        verify(vehicleDaoMock,times(1)).save(captor.capture());
        matcher.performAsserts(vehicle,captor.getValue());
    }

    /**
     * Test: the response of a post request for a vehicle that already exists  (error)
     */
    @Ignore //case bespreken wanneer een vehicle al bestaat
    @Test
    public void postVehicleAlreadyExists() throws Exception {
        when(vehicleDaoMock.find(anyInt())).thenReturn(vehicle);
        mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void putVehicleNoError() throws Exception {
        when(vehicleDaoMock.save(any())).thenReturn(vehicle);
        ResultActions resultActions=
                mockMvc.perform(put("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());
        matcher.jsonMatcher(resultActions,vehicle);

        verify(vehicleDaoMock,times(1)).save(captor.capture());
        matcher.performAsserts(vehicle,captor.getValue());
    }

    @Test
    public void putVehicleNotFound() throws Exception {
        when(vehicleDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    @Ignore //not implemented
    @Test
    public void destroyVehicleNoError() throws Exception
    {
        when(vehicleDaoMock.destroy(any())).thenReturn(vehicle);

        ArgumentCaptor<Vehicle> captor =ArgumentCaptor.forClass(Vehicle.class);

        mockMvc.perform(delete("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(vehicle.getId()));
        verify(vehicleDaoMock,times(1)).destroy(captor.capture());
        assertEquals("correct id destroy request",vehicle.getId(),captor.getValue().getId());

    }

    @Ignore //not implemented
    @Test
    public void destroyVehicleNotFound() throws Exception {
        when(vehicleDaoMock.destroy(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a destroy request for a vehicle that exists
     */
    @Ignore //on hold
    @Test
    public void destroyUser_noError() throws Exception {
        when(vehicleDaoMock.destroy(any())).thenReturn(vehicle);
        mockMvc.perform(delete("/vehicles").contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
    }

    /**
     * Test: the response of a destroy request for a vehicle that doesn't exist
     */
    @Ignore //on hold
    @Test
    public void destroyUser_notFound() throws Exception {
        when(vehicleDaoMock.destroy(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

}

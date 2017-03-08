package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Vehicle;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.vehicle.VehicleDao;
import solvas.rest.controller.VehicleRestController;

import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VehicleRestControllerTest {
    @Mock
    private VehicleDao vehicleDaoMock;

    @InjectMocks
    private VehicleRestController vehicleRestController;

    private MockMvc mockMvc;

    private Vehicle createDefaultVehicle()
    {
        Vehicle v= new Vehicle("1-AOR-430","","",null,20,1999,null,10,null,"car.be");
        v.setId(new Random().nextInt(100));
        return v;

    }


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(vehicleRestController).build();
    }

    @Test
    public void getVehicleById_noError() throws Exception
    {
        Vehicle vehicle = createDefaultVehicle();
        when(vehicleDaoMock.find(anyInt())).thenReturn(vehicle);
        mockMvc.perform(get("/vehicles/33"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id").value(vehicle.getId()))
                .andExpect(jsonPath("licensePlate").value(vehicle.getLicensePlate()));
    }

    @Test
    public void getVehicleById_notFound() throws Exception
    {
        when(vehicleDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getVehicles_noError() throws Exception
    {
        when(vehicleDaoMock.findAll()).thenReturn(Collections.singletonList(createDefaultVehicle()));
        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void postVehicle_noError() throws Exception {
        Vehicle vehicle = createDefaultVehicle();
        String json = new ObjectMapper().writeValueAsString(vehicle);
        when(vehicleDaoMock.save(any())).thenReturn(vehicle);
        mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(vehicle.getId()));
        verify(vehicleDaoMock,times(1)).save(any());
    }

    @Ignore //case bespreken wanneer een vehicle al bestaat
    @Test
    public void postVehicle_alreadyExists() throws Exception {
        Vehicle vehicle = createDefaultVehicle();
        String json=new ObjectMapper().writeValueAsString(vehicle);
        when(vehicleDaoMock.find(anyInt())).thenReturn(vehicle);
        mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void putVehicle_noError() throws Exception {

        Vehicle update = createDefaultVehicle();

        String json=new ObjectMapper().writeValueAsString(update);

        ArgumentCaptor<Vehicle> captor =ArgumentCaptor.forClass(Vehicle.class);

        when(vehicleDaoMock.save(any())).thenReturn(update);
        mockMvc.perform(put("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(update.getId()))
                .andExpect(jsonPath("licensePlate").value(update.getLicensePlate()));

        verify(vehicleDaoMock,times(1)).save(captor.capture());
        assertEquals("Correct id",update.getId(),captor.getValue().getId());
        assertEquals("Correct licensePlate",update.getLicensePlate(),captor.getValue().getLicensePlate());

    }

    @Test
    public void putVehicle_notFound() throws Exception {
        Vehicle vehicle = createDefaultVehicle();
        String json = new ObjectMapper().writeValueAsString(vehicle);
        when(vehicleDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    @Ignore //not implemented
    @Test
    public void destoryVehicle_noError() throws Exception
    {
        Vehicle vehicle=createDefaultVehicle();
        String json = new ObjectMapper().writeValueAsString(vehicle);
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
    public void destroyVehicle_notFound() throws Exception {
        Vehicle vehicle = createDefaultVehicle();
        String json = new ObjectMapper().writeValueAsString(vehicle);
        when(vehicleDaoMock.destroy(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/vehicles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

}

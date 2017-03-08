package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Company;
import solvas.models.Vehicle;
import solvas.models.Vehicletype;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.vehicle.VehicleDao;
import solvas.rest.controller.VehicleRestController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VehicleRestControllerTest extends AbstractRestTest {
    @Mock
    private VehicleDao vehicleDaoMock;

    @InjectMocks
    private VehicleRestController vehicleRestController;

    private MockMvc mockMvc;

    private Vehicle vehicle;

    private List<Vehicle> vehicles;

    private TestObjectProvider provider=new TestObjectProvider();


    @Before
    public void setUp()
    {
        vehicle=provider.getVehicle();
        vehicles=provider.getVehicles();
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(vehicleRestController).build();
    }

    @Test
    public void getVehicleById_noError() throws Exception
    {
        when(vehicleDaoMock.find(anyInt())).thenReturn(vehicle);
        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(json(vehicle)));

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
    }
}

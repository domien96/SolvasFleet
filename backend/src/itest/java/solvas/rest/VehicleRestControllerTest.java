package solvas.rest;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ViewResolver;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.greencard.GreenCardViewResolver;
import solvas.rest.greencard.pdf.GreenCardPdfView;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Vehicle;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.VehicleRestController;
import solvas.service.AbstractService;
import solvas.service.VehicleService;

import java.time.LocalDateTime;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests of the VehicleRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
public class VehicleRestControllerTest extends AbstractRestControllerTest<Vehicle,ApiVehicle> {
    @Mock
    private VehicleService vehicleService;

    @Mock
    private Validator validator;

    @Mock
    private DaoContext daoContext;

    @Mock
    private FleetSubscriptionDao fleetSubscriptionDao;

    @Mock
    private VehicleDao vehicleDao;
    /**
     * Constructor for specific VehicleController tests
     */
    public VehicleRestControllerTest() {
        super(ApiVehicle.class);
    }

    @Override
    MockMvc getMockMvc() {
        ViewResolver greenCardViewResolver = new GreenCardViewResolver(new GreenCardPdfView(daoContext));
        return MockMvcBuilders
                .standaloneSetup(getController())
                .setViewResolvers(greenCardViewResolver)
                .build();
    }

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
      //  super.setUp();
        when(daoContext.getFleetSubscriptionDao()).thenReturn(fleetSubscriptionDao);
        when(daoContext.getVehicleDao()).thenReturn(vehicleDao);
    }

    /**
     * Match jsonmodel with ApiCompany
     * @param res the ResultAction that mockMvc provides.
     * @param vehicle the vehicle we want to compare with the json result
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
        return new VehicleRestController(vehicleService, validator);
    }

    @Override
    ApiVehicle getTestModel()
    {
        ApiVehicle vehicle = super.getTestModel();
        vehicle.setValue(1500);
        vehicle.setYear(LocalDateTime.of(1990,1,1,0,0));
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
        return RestTestFixtures.VEHICLE_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.VEHICLE_ID_URL;
    }

    @Test
    public void greenCardDoesNotError() throws Exception {
        when(getService().getById(anyInt())).thenReturn(getTestModel());
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_ID_URL+"/greencard.pdf"))
                .andExpect(status().is2xxSuccessful());
    }
}

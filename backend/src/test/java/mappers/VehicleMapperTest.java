package mappers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import solvas.service.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.*;
import solvas.service.mappers.VehicleMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.rest.api.models.ApiVehicle;

import java.util.Optional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Tests to check correct mapping of a Vehicle
 */
public class VehicleMapperTest {
    private VehicleMapper mapper;

    @Mock
    private DaoContext context;

    @Mock
    private VehicleDao vehicleDao;

    @Mock
    private CompanyDao companyDao;

    @Mock
    private VehicleTypeDao vehicleTypeDao;

    @Mock
    private FleetDao fleetDao;

    @Mock
    private FleetSubscriptionDao fleetSubscriptionDao;

    /**
     * Setting up the tests of VehicleMapper
     */
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mapper=new VehicleMapper(context);
        when(context.getVehicleDao()).thenReturn(vehicleDao);
        when(context.getCompanyDao()).thenReturn(companyDao);
        when(context.getVehicleTypeDao()).thenReturn(vehicleTypeDao);
        when(context.getFleetDao()).thenReturn(fleetDao);
        when(context.getFleetSubscriptionDao()).thenReturn(fleetSubscriptionDao);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    /**
     * Test the conversion ApiVehicle->Vehicle
     */
    @Test
    public void convertToVehicle() throws EntityNotFoundException, DependantEntityNotFound {
        ApiVehicle apiVehicle = random(ApiVehicle.class);
        Vehicle random = random(Vehicle.class);
        random.setId(apiVehicle.getId());
        apiVehicle.setFleet(0);
        when(vehicleDao.find(anyInt())).thenReturn(random);
        when(fleetSubscriptionDao.activeForVehicle(any(Vehicle.class))).thenReturn(Optional.empty());
        Vehicle mapped = mapper.convertToModel(apiVehicle);

        assertThat(apiVehicle.getId(),is(mapped.getId()));
        assertThat(apiVehicle.getValue(),is(mapped.getValue()));
        assertThat(apiVehicle.getBrand(),is(mapped.getBrand()));
        //assertThat(apiVehicle.getFleet(),is(mapped.get()));
        assertThat(apiVehicle.getYear(),is(mapped.getYear()));
        assertThat(apiVehicle.getMileage(),is(mapped.getKilometerCount()));
//        assertThat(apiVehicle.getLeasingCompany(),is(mapped.getLeasingCompany().getId()));

    }

    /**
     * Test the conversion Vehicle->ApiVehicle
     */
    @Test
    public void convertToApiVehicle()
    {
        final int year= 1800;
        Vehicle vehicle = random(Vehicle.class);
        vehicle.setYear(year); // Year cannot be that random
        when(fleetSubscriptionDao.activeForVehicle(any(Vehicle.class))).thenReturn(Optional.empty());
        ApiVehicle converted = mapper.convertToApiModel(vehicle);

        assertThat(converted.getId(),is(vehicle.getId()));
        assertThat(converted.getLeasingCompany(),is(vehicle.getLeasingCompany().getId()));
        assertThat(converted.getValue(),is(vehicle.getValue()));
        assertThat(converted.getUrl(),is("http://localhost/vehicles/"+vehicle.getId()));
        assertThat(converted.getMileage(),is(vehicle.getKilometerCount()));
        assertThat(converted.getYear(),is(vehicle.getYear()));
        assertThat(converted.getBrand(),is(vehicle.getBrand()));
        assertThat(converted.getLicensePlate(),is(vehicle.getLicensePlate()));
        assertThat(converted.getModel(),is(vehicle.getModel()));
        //getFleet?
        //getVin?
    }
}

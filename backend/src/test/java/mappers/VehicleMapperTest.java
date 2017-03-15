package mappers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import solvas.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.*;
import solvas.rest.api.mappers.VehicleAbstractMapper;
import solvas.rest.api.models.ApiVehicle;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Tests to check correct mapping of the vehicle types
 */
public class VehicleMapperTest {
    private VehicleAbstractMapper mapper;

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

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mapper=new VehicleAbstractMapper(context);
        when(context.getVehicleDao()).thenReturn(vehicleDao);
        when(context.getCompanyDao()).thenReturn(companyDao);
        when(context.getVehicleTypeDao()).thenReturn(vehicleTypeDao);
        when(context.getFleetDao()).thenReturn(fleetDao);
        when(context.getFleetSubscriptionDao()).thenReturn(fleetSubscriptionDao);
    }

    @Ignore
    @Test
    public void convertToVehicle()
    {
        ApiVehicle apiVehicle = random(ApiVehicle.class);
        Vehicle random = random(Vehicle.class);
        random.setId(apiVehicle.getId());
        when(vehicleDao.find(anyInt())).thenReturn(random);
        Vehicle mapped = mapper.convertToModel(apiVehicle);

        assertThat(apiVehicle.getId(),is(mapped.getId()));
        assertThat(apiVehicle.getValue(),is(mapped.getValue()));
        assertThat(apiVehicle.getBrand(),is(mapped.getBrand()));
        //assertThat(apiVehicle.getFleet(),is(mapped.get()));
        assertThat(apiVehicle.getYear(),is(mapped.getYear()));
        assertThat(apiVehicle.getMileage(),is(mapped.getKilometerCount()));
        assertThat(apiVehicle.getLeasingCompany(),is(mapped.getLeasingCompany().getId()));

    }

    @Ignore
    @Test
    public void convertToApiVehicle()
    {
        Vehicle vehicle = random(Vehicle.class);
        ApiVehicle converted = mapper.convertToApiModel(vehicle);

        assertThat(converted.getId(),is(vehicle.getId()));
        assertThat(converted.getLeasingCompany(),is(vehicle.getLeasingCompany().getId()));
        assertThat(converted.getValue(),is(vehicle.getValue()));
        assertThat(converted.getUrl(),is("/vehicles/"+vehicle.getId()));
        assertThat(converted.getMileage(),is(vehicle.getKilometerCount()));
        assertThat(converted.getYear(),is(vehicle.getYear()));
        assertThat(converted.getBrand(),is(vehicle.getBrand()));
        assertThat(converted.getLicensePlate(),is(vehicle.getLicensePlate()));
        assertThat(converted.getModel(),is(vehicle.getModel()));
        //getFleet?
        //getVin?
    }
}

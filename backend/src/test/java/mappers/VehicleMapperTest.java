package mappers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import solvas.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.mappers.VehicleAbstractMapper;
import solvas.rest.api.models.ApiVehicle;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Test to check correct mapping of the vehicle types
 */
public class VehicleMapperTest {
    private VehicleAbstractMapper mapper;

    @Mock
    private DaoContext context;

    @Mock
    private VehicleDao vehicleDao;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mapper=new VehicleAbstractMapper(context);
        when(context.getVehicleDao()).thenReturn(vehicleDao);
    }

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

    }
}

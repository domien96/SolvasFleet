package mappers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shared.AbstractSolvasTest;
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
public class VehicleMapperTest extends AbstractSolvasTest<Vehicle, ApiVehicle> {
    private VehicleMapper mapper;

    public VehicleMapperTest() {
        super(Vehicle.class,ApiVehicle.class);
    }

    /**
     * Setting up the tests of VehicleMapper
     */
    @Before
    public void setUp()
    {
        super.setUp();
        mapper=new VehicleMapper(getDaoContext());
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    /**
     * Test the conversion ApiVehicle->Vehicle
     */
    @Test
    public void convertToVehicle() throws EntityNotFoundException, DependantEntityNotFound {
        when(getDaoContext().getVehicleDao().find(anyInt())).thenReturn(getModel());
        
        when(getDaoContext().getFleetSubscriptionDao().activeForVehicle(any(Vehicle.class))).thenReturn(Optional.empty());
        Vehicle mapped = mapper.convertToModel(getApiModel());

        assertThat(getApiModel().getId(),is(mapped.getId()));
        assertThat(getApiModel().getValue(),is(mapped.getValue()));
        assertThat(getApiModel().getBrand(),is(mapped.getBrand()));
        assertThat(getApiModel().getYear(),is(mapped.getYear()));
        assertThat(getApiModel().getMileage(),is(mapped.getKilometerCount()));

    }

    /**
     * Test the conversion Vehicle->ApiVehicle
     */
    @Test
    public void convertToApiVehicle()
    {
        Vehicle vehicle = getModel();
        vehicle.setYear(1990);


        when(getDaoContext().getFleetSubscriptionDao().activeForVehicle(any(Vehicle.class))).thenReturn(Optional.empty());
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

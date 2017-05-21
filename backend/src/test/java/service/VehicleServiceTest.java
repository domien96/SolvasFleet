package service;

import org.junit.Before;
import org.mockito.Mock;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.models.ApiVehicle;
import solvas.service.AbstractService;
import solvas.service.VehicleService;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.VehicleMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Vehicle;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Test the vehicle service.
 */
public class VehicleServiceTest extends AbstractServiceTest<Vehicle, ApiVehicle> {

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private VehicleDao vehicleDao;
    @Mock
    private FleetSubscriptionDao fleetSubscriptionDao;

    @Mock
    private VehicleMapper vehicleMapper;

    /**
     * Construct the test.
     */
    public VehicleServiceTest() {
        super(Vehicle.class, ApiVehicle.class);
    }

    @Before
    @Override
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getVehicleDao()).thenReturn(vehicleDao);
        when(daoContextMock.getFleetSubscriptionDao()).thenReturn(fleetSubscriptionDao);
        when(fleetSubscriptionDao.findByVehicleAndEndDateIsNull(any(Vehicle.class))).thenReturn(Optional.empty());
    }

    @Override
    protected AbstractService<Vehicle, ApiVehicle> getService() {
        return new VehicleService(daoContextMock, vehicleMapper);
    }

    @Override
    protected Dao<Vehicle> getDaoMock() {
        return vehicleDao;
    }

    @Override
    protected AbstractMapper<Vehicle, ApiVehicle> getMapperMock() {
        return vehicleMapper;
    }
}

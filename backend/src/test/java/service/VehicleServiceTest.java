package service;

import org.junit.Before;
import org.mockito.Mock;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.models.ApiVehicle;
import solvas.service.AbstractService;
import solvas.service.VehicleService;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.VehicleMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Vehicle;

import static org.mockito.Mockito.when;

public class VehicleServiceTest extends AbstractServiceTest<Vehicle,ApiVehicle>{

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private VehicleDao vehicleDao;

    @Mock
    private VehicleMapper vehicleMapper;

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getVehicleDao()).thenReturn(vehicleDao);
    }

    public VehicleServiceTest() {
        super(Vehicle.class, ApiVehicle.class);
    }


    @Override
    protected AbstractService<Vehicle,ApiVehicle> getService() {
        return new VehicleService(daoContextMock,vehicleMapper);
    }

    @Override
    protected Dao getDaoMock() {
        return vehicleDao;
    }

    @Override
    protected AbstractMapper<Vehicle,ApiVehicle> getMapperMock() {
        return vehicleMapper;
    }
}
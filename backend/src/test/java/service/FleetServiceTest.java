package service;

import org.junit.Before;
import org.mockito.Mock;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FleetDao;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.rest.api.models.ApiFleet;
import solvas.service.AbstractService;
import solvas.service.FleetService;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.FleetMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Fleet;

import static org.mockito.Mockito.when;

public class FleetServiceTest extends AbstractServiceTest<Fleet,ApiFleet>{

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private FleetDao fleetDao;
    @Mock
    private FleetSubscriptionDao fleetSubscriptionDao;

    @Mock
    private FleetMapper fleetMapper;

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getFleetDao()).thenReturn(fleetDao);
        when(daoContextMock.getFleetSubscriptionDao()).thenReturn(fleetSubscriptionDao);

    }

    public FleetServiceTest() {
        super(Fleet.class, ApiFleet.class);
    }


    @Override
    protected AbstractService<Fleet, ApiFleet> getService() {
        return new FleetService(daoContextMock, fleetMapper);
    }

    @Override
    protected Dao<Fleet> getDaoMock() {
        return fleetDao;
    }

    @Override
    protected AbstractMapper<Fleet, ApiFleet> getMapperMock() {
        return fleetMapper;
    }
}

package mappers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.api.dao.FleetDao;
import solvas.rest.api.models.ApiFleet;
import solvas.service.mappers.FleetMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Company;
import solvas.service.models.Fleet;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Tests to check correct mapping of a fleet
 */
public class FleetMapperTest {

    @Mock
    private DaoContext daoContext;

    @Mock
    private FleetDao fleetDaoMock;

    @Mock
    private CompanyDao companyDaoMock;
    private FleetMapper mapper;


    /**
     * Setting up the tests of FleetMapper
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(daoContext.getFleetDao()).thenReturn(fleetDaoMock);
        when(daoContext.getCompanyDao()).thenReturn(companyDaoMock);
        mapper = new FleetMapper(daoContext);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    /**
     * Test the conversion Fleet->ApiFleet
     */
    @Test
    public void convertToApiFleet() {
        Fleet fleet = random(Fleet.class);
        ApiFleet converted = mapper.convertToApiModel(fleet);
        assertThat(converted.getId(), is(fleet.getId()));
        assertThat(converted.getUrl(), is("http://localhost/fleets/" + fleet.getId()));
        assertThat(converted.getName(), is(fleet.getName()));
        assertThat(converted.getCompany(), is(fleet.getCompany().getId()));
        assertThat(converted.getUpdatedAt(), is(fleet.getUpdatedAt()));
        assertThat(converted.getCreatedAt(), is(fleet.getCreatedAt()));
    }

    /**
     * Test the conversion ApiFleet->Fleet
     */
    @Test
    public void convertToFleet() throws DependantEntityNotFound, EntityNotFoundException {
        ApiFleet apiFleet = random(ApiFleet.class);
        Fleet random = random(Fleet.class);
        random.setId(apiFleet.getId());
        when(fleetDaoMock.find(anyInt())).thenReturn(random);
        Company randomCompany = random(Company.class);
        randomCompany.setId(apiFleet.getCompany());
        when(companyDaoMock.find(anyInt())).thenReturn(randomCompany);

        Fleet converted = mapper.convertToModel(apiFleet);
        assertThat(converted.getId(), is(apiFleet.getId()));
        assertThat(converted.getName(), is(apiFleet.getName()));
        assertThat(converted.getCompany().getId(), is(apiFleet.getCompany()));
    }
}
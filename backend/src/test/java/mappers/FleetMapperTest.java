package mappers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import solvas.models.Fleet;
import solvas.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.FleetMapper;
import solvas.rest.api.models.ApiFleet;
import solvas.rest.api.models.ApiVehicle;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Tests to check correct mapping of the a fleet
 */
public class FleetMapperTest {

    @Mock
    private DaoContext daoContext;

    private FleetMapper mapper;


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mapper=new FleetMapper(daoContext);
    }

    @Test
    public void convertToApiVehicle()
    {
        Fleet fleet = random(Fleet.class);
        ApiFleet converted = mapper.convertToApiModel(fleet);
        assertThat(converted.getId(),is(fleet.getId()));
        assertThat(converted.getUrl(),is("/fleets/"+fleet.getId()));
        assertThat(converted.getName(),is(fleet.getName()));
        assertThat(converted.getCompany(),is(fleet.getCompany().getId()));
        assertThat(converted.getUpdatedAt(),is(fleet.getUpdatedAt()));
        assertThat(converted.getCreatedAt(),is(fleet.getCreatedAt()));
    }

    @Ignore
    @Test
    public void convertToVehicle()
    {

    }

}

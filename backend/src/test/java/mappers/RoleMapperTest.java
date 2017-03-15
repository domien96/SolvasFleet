package mappers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import solvas.models.Role;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.RoleDao;
import solvas.rest.api.mappers.RoleAbstractMapper;
import solvas.rest.api.models.ApiRole;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class RoleMapperTest {

    private RoleAbstractMapper mapper;

    @Mock
    private DaoContext context;

    @Mock
    private RoleDao roleDaoMock;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mapper=new RoleAbstractMapper(context);
        when(context.getRoleDao()).thenReturn(roleDaoMock);
    }

    @Test
    public void convertToApiRole()
    {
        Role role = random(Role.class);
        ApiRole converted = mapper.convertToApiModel(role);

        assertThat(converted.getId(),is(role.getId()));
        assertThat(converted.getCompany(),is(role.getCompany().getId()));
        assertThat(converted.getFunction(),is(role.getFunction()));
        assertThat(converted.getUser(),is(role.getUser().getId()));
        assertThat(converted.getCreatedAt(),is(role.getCreatedAt()));
        assertThat(converted.getUpdatedAt(),is(role.getUpdatedAt()));
        assertThat(converted.getUrl(),is("/roles/"+role.getId()));
        assertThat(converted.getStartDate(),is(role.getStartDate()));
        assertThat(converted.getEndDate(),is(role.getEndDate()));
    }
}

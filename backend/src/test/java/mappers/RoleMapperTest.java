package mappers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.RoleDao;
import solvas.rest.api.models.ApiRole;
import solvas.service.mappers.RoleMapper;
import solvas.service.models.Role;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Tests of the RoleMapper
 */
public class RoleMapperTest {

    private RoleMapper mapper;

    @Mock
    private DaoContext context;

    @Mock
    private RoleDao roleDaoMock;

    /**
     * Setting up the tests of RoleMapper
     */
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mapper=new RoleMapper(context);
        when(context.getRoleDao()).thenReturn(roleDaoMock);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    /**
     * Test the conversion Role->ApiRole
     */
    @Test
    public void convertToApiRole()
    {
        Role role = random(Role.class);
        ApiRole converted = mapper.convertToApiModel(role);

        assertThat(converted.getId(),is(role.getId()));
        assertThat(converted.getFunction(),is(role.getFunction()));
        assertThat(converted.getCreatedAt(),is(role.getCreatedAt()));
        assertThat(converted.getUpdatedAt(),is(role.getUpdatedAt()));
        assertThat(converted.getUrl(),is("http://localhost/auth/roles/"+role.getId()));
    }
}

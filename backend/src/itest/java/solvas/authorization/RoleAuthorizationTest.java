package solvas.authorization;

import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiRole;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test Role routes authorization
 */
public class RoleAuthorizationTest extends AbstractAuthorizationTest {

    @Override
    public String getUrl() {
        return RestTestFixtures.ROLE_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.ROLE_ID_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiRole role = new ApiRole();
        role.setName("Whatever");
        role.setUser(1);
        role.setId(1);
        role.setPermissions(new HashSet<>(Arrays.asList("RCompany")));
        return role;
    }



    @Override
    public void userCanDeleteModel() throws Exception {
        getMockMvc().perform(auth(delete(getUrl()+"/2"),adminToken)).andExpect(status().isNoContent());
    }
}

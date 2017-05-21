package solvas.authorization;

import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiUser;
import solvas.service.models.Model;

/**
 * Test User routes authorization
 */
public class UserAuthorizationTest extends AbstractAuthorizationTest {


    @Override
    public String getUrl() {
        return RestTestFixtures.USER_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.USER_ID_URL;
    }

    @Override
    public String getModelJson() {
        return "{\"id\":\"1\",\"email\":\"ea@a.be\",\"firstName\":\"aa\",\"lastName\":\"bb\",\"password\":\"secret\"}";
    }

    @Override
    public ApiModel getModel() {
        ApiUser user = new ApiUser();
        user.setLastName("bob");
        user.setLastName("ra");
        user.setEmail("bob@ra.be");
        return user;
    }
}

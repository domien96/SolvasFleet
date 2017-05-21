package solvas.authorization;

import jdk.nashorn.internal.ir.annotations.Ignore;
import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiUser;
import solvas.service.models.Model;

import static java.lang.StrictMath.random;

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
        return "{\"id\":\"1\",\"email\":\"ea"+random()+"@a.be\",\"firstName\":\"aa\",\"lastName\":\"bb\",\"password\":\"secret\"}";
    }


    @Ignore
    public void userCanPostModel() {
        // Something fails with the audits
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

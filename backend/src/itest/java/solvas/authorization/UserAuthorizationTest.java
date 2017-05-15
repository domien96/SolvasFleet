package solvas.authorization;


import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiUser;
import solvas.service.models.Model;

public class UserAuthorizationTest extends AbstractAuthorizationTest {


    @Override
    public String getUrl() {
        return RestTestFixtures.USER_ROOT_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiUser user = new ApiUser();
        user.setPassword("ab");
        user.setEmail("ea@a.be");
        return user ;
    }
}

package solvas.authorization;

import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiPermission;

/**
 * Test Permission routes authorization
 */
public class PermissionAuthorizationTest extends AbstractAuthorizationTest {
    @Override
    public String getUrl() {
        return RestTestFixtures.PERMISSION_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return "";
    }

    @Override
    public ApiModel getModel() {
        ApiPermission permission = new ApiPermission();
        permission.setId(1);
        permission.setScope("archive:companies");
        return permission;
    }

    /**
     * The following functions aren't tested because they can't happen
     */
    @Override
    public void userCanPostModel() {}

    @Override
    public void userCantPostModel() {}

    @Override
    public void userCanDeleteModel() {}

    @Override
    public void userCantDeleteModel() {}

    @Override
    public void userCanReadModel() {}

    @Override
    public void userCantReadModel() {}
}

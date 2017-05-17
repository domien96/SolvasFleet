package solvas.authorization;

import com.sun.org.apache.regexp.internal.RE;
import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiFunction;
import solvas.rest.api.models.ApiModel;

import java.time.LocalDateTime;

public class FunctionAuthorizationTest extends AbstractAuthorizationTest {

    @Override
    public String getUrl() {
        return RestTestFixtures.FUNCTION_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.FUNCTION_ID_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiFunction function = new ApiFunction();
        function.setCompany(1);
        function.setId(1);
        function.setUser(1);
        function.setRole(1);
        function.setStartDate(LocalDateTime.now().minusDays(100));
        function.setEndDate(LocalDateTime.now().plusDays(100));
        return function;
    }

    /**
     * The following functions aren't supported, so we skip the tests
     */

    @Override
    public void userCanPutModel() {}

    @Override
    public void userCantPutModel() {}

    @Override
    public void userCanReadModel() {}

    @Override
    public void userCantReadModel() {}
}

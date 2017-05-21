package solvas.authorization;

import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiContract;
import solvas.rest.api.models.ApiModel;

import java.time.LocalDateTime;

/**
 * Test contract routes authorization
 */
public class ContractAuthorizationTest extends AbstractAuthorizationTest {

    @Override
    public String getUrl() {
        return RestTestFixtures.CONTRACT_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.CONTRACT_ID_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiContract c = new ApiContract();
        c.setInsuranceCompany(1);
        c.setId(1);
        c.setVehicle(1);
        c.setFranchise(100);
        c.setPremium(100);
        c.setStartDate(LocalDateTime.now().minusDays(100));
        c.setEndDate(LocalDateTime.now().plusDays(100));
        c.setType("Omnium");
        return c;
    }
}

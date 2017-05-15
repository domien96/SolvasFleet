package solvas.authorization;

import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiTax;

public class TaxAuthorizationTest extends AbstractAuthorizationTest {
    @Override
    public String getUrl() {
        return RestTestFixtures.TAX_BASE_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiTax tax = new ApiTax();
        tax.setId(1);
        tax.setTax(31.4);
        tax.setVehicleType("PersonalVehicle");
        tax.setContractType("billing");
        return tax;
    }
}

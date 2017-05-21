package solvas.authorization;

import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiTax;

/**
 * In this class we will test GET AND PUT to /vehicles/types/VehicleType/taxes/ContractType
 */
public class TaxAuthorizationTest extends AbstractAuthorizationTest {
    @Override
    public String getUrl() {
        return RestTestFixtures.TAX_BASE_URL;
    }

    @Override
    public String getIdUrl() {
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

    // Everyone can read taxes.
    @Override
    public void userCantReadModel() {

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
    public void userCanReadModels() {}

    @Override
    public void userCantReadModels() {}
}

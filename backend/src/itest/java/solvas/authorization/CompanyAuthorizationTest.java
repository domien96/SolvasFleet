package solvas.authorization;

import org.junit.Test;
import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiModel;

import java.util.ArrayList;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CompanyAuthorizationTest extends AbstractAuthorizationTest{

    @Override
    public String getUrl() {
        return RestTestFixtures.COMPANY_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.COMPANY_ID_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiCompany company = random(ApiCompany.class);
        company.setId(1);
        company.setPhoneNumber("093177898");
        company.setVatNumber("BE0428759497");
        company.setType("InsuranceCompany");
        return company;
    }

    @Override
    public void userCantReadModels() throws Exception {
        getMockMvc().perform(auth(get(getUrl()),nopermissionToken))
                .andExpect(jsonPath("data").value(new ArrayList<ApiCompany>()));
    }
}

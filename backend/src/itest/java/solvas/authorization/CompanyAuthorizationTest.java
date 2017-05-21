package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import solvas.persistence.api.dao.CompanyDao;
import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiModel;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Test authorization on company rest controller.
 */
public class CompanyAuthorizationTest extends AbstractAuthorizationTest {
    @Autowired
    private CompanyDao companyDao;

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

    /**
     * User has no permission on any company, expect empty list.
     * @throws Exception When test fails
     */
    @Override
    public void userCantReadModels() throws Exception {
        getMockMvc().perform(auth(get(getUrl()), nopermissionToken))
                .andExpect(jsonPath("data", hasSize(0)));
    }

    /**
     * User has permission on every company, expect all companies
     * @throws Exception When test fails
     */
    @Override
    public void userCanReadModels() throws Exception {
        System.out.println(companyDao.count());
        getMockMvc().perform(auth(get(getUrl()),adminToken))
                .andExpect(jsonPath("data", hasSize((int) companyDao.count())));
    }

}

package solvas.persistence.api.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.service.models.Company;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Integration tests for custom behaviour on the company DAO.
 *
 * @author Niko Strijbol
 * @author Karel Vandenbussche
 */
public class CompanyDaoTest extends DaoTest {

    @Autowired
    private CompanyDao companyDao;

    /**
     * Test finding companies with a certain name.
     */
    @Test
    public void withName() {

        Company company = manager.find(Company.class, 1);
        manager.detach(company);
        // Test existing
        assertThat(companyDao.findByName(company.getName()), hasSize(1));
        // Save and test again.
        company.setId(5);
        companyDao.save(company);
        assertThat(companyDao.findByName(company.getName()), hasSize(2));
    }
}
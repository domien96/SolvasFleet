package solvas.persitence.api.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.models.Company;
import solvas.persistence.api.dao.CompanyDao;

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

    private Company company;

    @Before
    public void setUp() {
        company = companyDao.find(1);
        // Detach the company.
        manager.detach(company);
    }

    /**
     * Test: finding certain companies with a name
     */
    @Test
    public void withName() {
        // Test existing
        assertThat(companyDao.findByName(company.getName()), hasSize(1));
        // Save and test again.
        company.setId(5);
        companyDao.save(company);
        assertThat(companyDao.findByName(company.getName()), hasSize(2));
    }
}
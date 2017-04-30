package solvas.persistence.api.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.service.models.Company;


import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

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
        company.setVatNumber("TestVAT");
        company.setId(0);
        companyDao.save(company);
        assertThat(companyDao.findByName(company.getName()), hasSize(2));
    }

    /**
     * Test finding companies with a certain vat.
     */
    @Test
    public void withVat() {
        List<Company> companies = companyDao.findAll();
        companies.forEach(company -> assertThat(companyDao.findByVatNumber(company.getVatNumber()).get().getId(),is(company.getId())));

    }


    /**
     * Test finding companies with a certain vat, which will throw an error
     */
    @Test(expected=NoSuchElementException.class)
    public void withVatError() {
        companyDao.findByVatNumber("").get();
    }

}
package dao;

import org.junit.Ignore;
import org.junit.Test;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.company.HibernateCompanyDao;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@Ignore
@Transactional
public class CompanyDaoTest {

    private CompanyDao companyDao=new HibernateCompanyDao();

    @Test
    public void addCompany()
    {

    }

    @Test
    public void destroyCompany()
    {

    }

    @Test
    public void updateCompany()
    {

    }

    @Test
    public void findCompanyById()
    {

    }

    @Test
    public void findCompanies()
    {

    }
}

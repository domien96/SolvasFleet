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
    public void testCreate()
    {
        assertEquals("Start with emprty db",0,companyDao.findAll().size());
      //  companyDao.save(new Company());
        assertEquals("added successfully",1,companyDao.findAll().size());
    }

    @Test
    public void testDelete()
    {
        /*Company c1=new Company();
        //c1.setId(1);
        //Company c2=new Company();
        c2.setId(2);
        companyDao.save(c1);
        companyDao.save(c2);
        assertEquals("not empty",2,companyDao.findAll().size());
        companyDao.destroy(c2);
        assertEquals("company is gone",null,companyDao.find(2));
        assertEquals("other company is still there",c1,companyDao.find(1));*/
    }

    @Test
    public void testUpdate()
    {
        /*Company c1=new Company();
        c1.setId(1);
        companyDao.save(c1);
        c1.setAddress("test");
        companyDao.save(c1);

        assertEquals("has been updated","test",companyDao.find(1).getAddress());*/
    }
    @Test
    public void testWithName()
    {
        //TBC
    }
}

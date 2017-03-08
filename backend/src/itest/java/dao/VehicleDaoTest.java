package dao;

import org.junit.Ignore;
import org.junit.Test;
import solvas.models.Vehicle;
import solvas.persistence.vehicle.HibernateVehicleDao;
import solvas.persistence.vehicle.VehicleDao;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@Ignore
@Transactional
public class VehicleDaoTest {

    private VehicleDao companyDao=new HibernateVehicleDao();

    @Test
    public void testAdd()
    {
        assertEquals("Empty at start",0,companyDao.findAll().size());
      /*  Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        companyDao.save(vehicle);
        assertEquals("Added",1,companyDao.findAll().size());
        assertEquals("Correct vehicle",vehicle,companyDao.find(1));*/
    }

    @Test
    public void testRemove()
    {
    }

    @Test
    public void testUpdate()
    {
    /*    Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setKilometerCount(100);
        companyDao.save(vehicle);
        assertEquals("Added",1,companyDao.findAll().size());
        assertEquals("Km=100 (old)",100,companyDao.find(1).getKilometerCount());
        vehicle.setKilometerCount(4000);
        companyDao.save(vehicle);
        assertEquals("updated km to 4000",4000,companyDao.find(1).getKilometerCount());
*/
    }
}

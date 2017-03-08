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

    private VehicleDao vehicleDao=new HibernateVehicleDao();

    @Test
    public void addVehicle()
    {

    }

    @Test
    public void destroyVehicle()
    {

    }

    @Test
    public void updateVehicle()
    {

    }

    @Test
    public void findVehicleById()
    {

    }

    @Test
    public void findVehicles()
    {

    }
}

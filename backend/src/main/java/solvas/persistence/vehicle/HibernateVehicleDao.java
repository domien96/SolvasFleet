package solvas.persistence.vehicle;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Vehicle;
import solvas.persistence.HibernateDao;

/**
 * Created by steve on 04/03/2017.
 */
@Repository
@Transactional
public class HibernateVehicleDao extends HibernateDao<Vehicle> implements VehicleDao {


    /**
     * Hibernate implementation for Vehicle.
     */
    public HibernateVehicleDao() {
        super(Vehicle.class);
    }
}

package solvas.persistence.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Vehicle;
import solvas.persistence.hibernate.HibernateDao;
import solvas.persistence.api.dao.VehicleDao;

/**
 * Hibernate implementation for vehicle.
 *
 * @author steven
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

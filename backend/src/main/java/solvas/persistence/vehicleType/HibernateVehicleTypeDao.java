package solvas.persistence.vehicleType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Vehicle;
import solvas.models.VehicleType;
import solvas.persistence.Filter;
import solvas.persistence.HibernateDao;

import javax.persistence.criteria.Join;
import java.util.Collection;

/**
 * Hibernate implementation for Company.
 *
 * @author Niko Strijbol
 */
@Repository
@Transactional
public class HibernateVehicleTypeDao extends HibernateDao<VehicleType> implements VehicleTypeDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateVehicleTypeDao() {
        super(VehicleType.class);
    }

    @Override
    public Collection<VehicleType> withType(String name) {
        return findAll(Filter.predicate((builder, root) -> {
            Join<Vehicle, VehicleType> join = root.join("type");
            return builder.equal(
                    join.get("name"),
                    name
            );
        }));
    }
}
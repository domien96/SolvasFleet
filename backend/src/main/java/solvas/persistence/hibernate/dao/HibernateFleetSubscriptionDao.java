package solvas.persistence.hibernate.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface HibernateFleetSubscriptionDao extends FleetSubscriptionDao {

    @Override
    @Query("select s from FleetSubscription s where s.vehicle = ?1 and s.startDate <= current_date and (s.endDate is null or s.endDate >= current_date) and s.archived = false")
    Optional<FleetSubscription> activeForVehicle(Vehicle vehicle);

    @Override
    @Query("select s from FleetSubscription s where s.fleet = ?1 and (s.endDate is null or s.endDate >= ?2) and s.startDate <= ?3 and s.archived = false")
    Collection<FleetSubscription> findByFleetAndInPeriod(Fleet fleet, LocalDate start, LocalDate end);
}

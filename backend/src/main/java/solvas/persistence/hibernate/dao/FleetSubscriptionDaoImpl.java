package solvas.persistence.hibernate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.persistence.api.dao.FleetSubscriptionDaoCustom;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Interface containing queries for some methods JPA couldn't interpret
 */
@Component
public class FleetSubscriptionDaoImpl implements FleetSubscriptionDaoCustom {
    private final EntityManager entityManager;

    /**
     * Create instance
     * @param entityManager to create queries
     */
    @Autowired
    public FleetSubscriptionDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<FleetSubscription> activeForVehicle(Vehicle vehicle) {
        TypedQuery<FleetSubscription> q = entityManager.createQuery("select s from FleetSubscription s where s.vehicle = ?1 and s.startDate <= current_date and (s.endDate is null or s.endDate >= current_date) and s.archived = false", FleetSubscription.class)
                .setParameter(1, vehicle);
        return Optional.ofNullable(DataAccessUtils.singleResult(q.getResultList()));
    }
    @Override
    public Collection<FleetSubscription> findByFleetAndStartDateAndEndDate(Fleet fleet, LocalDate start, LocalDate end) {
        return entityManager
                .createQuery("select s from FleetSubscription s where s.fleet = ?1 and (s.endDate is null or s.endDate >= ?2) and s.startDate <= ?3 and s.archived = false", FleetSubscription.class)
                .setParameter(1, fleet)
                .setParameter(2, start)
                .setParameter(3, end)
                .getResultList();
    }
}

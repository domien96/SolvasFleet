package solvas.persistence.hibernate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Component;
import solvas.persistence.api.dao.FleetSubscriptionDaoCustom;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Interface containing queries for some methods JPA couldn't interpret.
 *
 * @author Niko Strijbol
 * @author David Vandorpe
 */
@Component
public class FleetSubscriptionDaoImpl implements FleetSubscriptionDaoCustom {

    private final EntityManager entityManager;

    /**
     * Create instance
     *
     * @param entityManager to create queries
     */
    @Autowired
    public FleetSubscriptionDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<FleetSubscription> activeForVehicle(Vehicle vehicle) {
        LocalDateTime now = LocalDateTime.now();
        return activeForVehicleBetween(vehicle, now, now);
    }

    @Override
    public Optional<FleetSubscription> activeForVehicleBetween(Vehicle vehicle, LocalDateTime start, LocalDateTime end) {

        TypedQuery<FleetSubscription> query = entityManager.createQuery(
                "select s from FleetSubscription s where s.vehicle = ?1 and s.startDate <= ?2 and (s.endDate is null or s.endDate > ?3) and s.archived = false",
                FleetSubscription.class)
                .setParameter(1, vehicle)
                .setParameter(2, start)
                .setParameter(3, end);

        return Optional.ofNullable(DataAccessUtils.singleResult(query.getResultList()));
    }
}
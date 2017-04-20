package solvas.persistence.hibernate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.service.models.Fleet;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

/**
 * Interface containing queries for some methods JPA couldn't interpret
 */
@Component
public class InvoiceDaoImpl {
    private final EntityManager entityManager;

    /**
     * Create instance
     * @param entityManager to create queries
     */
    @Autowired
    public InvoiceDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public LocalDateTime latestEndDateByFleet(Fleet fleet) {
        return entityManager
                .createQuery("SELECT max(i.endDate) FROM Invoice i WHERE i.fleet = ?1", LocalDateTime.class)
                .setParameter(1, fleet)
                .getSingleResult();
    }
}

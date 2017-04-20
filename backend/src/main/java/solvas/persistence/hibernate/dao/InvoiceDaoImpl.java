package solvas.persistence.hibernate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import solvas.persistence.api.dao.InvoiceDao;
import solvas.persistence.api.dao.InvoiceDaoCustom;
import solvas.service.models.Fleet;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

/**
 * Interface containing queries for some methods JPA couldn't interpret
 */
@Component
public class InvoiceDaoImpl implements InvoiceDaoCustom {
    private final EntityManager entityManager;

    @Autowired
    public InvoiceDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public LocalDateTime latestEndDateByFleet(Fleet fleet) {
        return entityManager
                .createQuery("SELECT max(i.endDate) FROM Invoice i WHERE i.fleet = ?1", LocalDateTime.class)
                .setParameter(1, fleet)
                .getSingleResult();
    }
}

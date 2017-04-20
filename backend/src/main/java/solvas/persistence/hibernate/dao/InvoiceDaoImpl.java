package solvas.persistence.hibernate.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import solvas.persistence.api.dao.InvoiceDao;
import solvas.service.models.Fleet;

import java.time.LocalDateTime;

/**
 * Interface containing queries for some methods JPA couldn't interpret
 */
@Component
public interface InvoiceDaoImpl extends InvoiceDao {
    @Override
    @Query("SELECT max(i.endDate) FROM Invoice i WHERE i.fleet = ?1")
    public LocalDateTime latestEndDateByFleet(Fleet fleet);
}

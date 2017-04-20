package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Fleet;
import solvas.service.models.Invoice;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * DAO for invoices.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InvoiceDao extends Dao<Invoice> {

    /**
     * Get all the invoices with given fleet
     *
     * @param fleet the required fleet
     *
     * @return a collection of invoices with given fleet
     */
    Collection<Invoice> findByFleet(Fleet fleet);

    /**
     * Get latest date for which an invoice was generated for a fleet
     * @param fleet
     * @return LocalDateTime
     */
    LocalDateTime latestEndDateByFleet(Fleet fleet);
}
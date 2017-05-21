package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Fleet;
import solvas.service.models.Invoice;
import solvas.service.models.InvoiceType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * DAO for invoices.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InvoiceDao extends Dao<Invoice> {

    /**
     * Find the newest invoice of a certain type and for a fleet.
     *
     * @param type The type of invoice.
     * @param fleet The fleet.
     *
     * @return The most recent invoice.
     */
    Optional<Invoice> findFirstByTypeAndFleetOrderByStartDateDesc(InvoiceType type, Fleet fleet);

    /**
     * Find all invoices of a given type between two dates (including the limits).
     *
     * @param type The type of invoice.
     * @param startDate The start date (inclusive).
     * @param endDate The end date (inclusive).
     *
     * @return The collection of invoices.
     */
    Collection<Invoice> findByTypeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(InvoiceType type, LocalDateTime startDate, LocalDateTime endDate);

}
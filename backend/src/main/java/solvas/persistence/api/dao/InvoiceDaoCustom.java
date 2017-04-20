package solvas.persistence.api.dao;

import solvas.service.models.Fleet;

import java.time.LocalDateTime;

/**
 * Custom methods to implement because JPA can't
 */
public interface InvoiceDaoCustom {
    /**
     * Get latest date for which an invoice was generated for a fleet
     * @param fleet
     * @return LocalDateTime
     */
    LocalDateTime latestEndDateByFleet(Fleet fleet);
}

package solvas.persistence.api.dao;

import solvas.service.models.Fleet;
import solvas.service.models.Invoice;

import java.time.LocalDateTime;
import java.util.Collection;

public interface InvoiceDaoCustom {
    /**
     * Get latest date for which an invoice was generated for a fleet
     * @param fleet
     * @return LocalDateTime
     */
    LocalDateTime latestEndDateByFleet(Fleet fleet);
}

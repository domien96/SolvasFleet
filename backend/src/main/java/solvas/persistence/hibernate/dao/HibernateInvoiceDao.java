package solvas.persistence.hibernate.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import solvas.persistence.api.dao.InvoiceDao;
import solvas.service.models.Fleet;
import solvas.service.models.Invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface HibernateInvoiceDao extends InvoiceDao {
    @Override
    @Query("SELECT MAX(i.endDate) FROM Invoice i WHERE i.fleet = ?1")
    LocalDateTime getLatestEndDateByFleet(Fleet fleet);
}

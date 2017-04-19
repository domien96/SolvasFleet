package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Invoice;

/**
 * DAO for invoices.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InvoiceDao extends Dao<Invoice> {

}
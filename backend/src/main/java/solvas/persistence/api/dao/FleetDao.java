package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.Company;
import solvas.service.models.Fleet;
import solvas.persistence.api.Dao;

import java.util.Collection;

/**
 * DAO for a fleet.
 *
 * @author Niko Strijbol
 */
@Repository
public interface FleetDao extends Dao<Fleet> {
    /**
     * Find all fleets with given company
     *
     * @param company the company of which the fleets are needed
     * @return Fleets with given company
     */
    Collection<Fleet> findByCompany(Company company);

}
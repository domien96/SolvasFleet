package solvas.persistence.api.dao;

import solvas.models.Fleet;
import solvas.persistence.api.Dao;

/**
 * DAO for a company.
 *
 * @author Niko Strijbol
 */
public interface FleetDao extends Dao<Fleet> {

    /**
     * Find all companies with a certain name.
     *
     * @param companyId The ID of the company..
     *
     * @return The companies.
     */
   // Collection<Fleet> findByCompanyId(int companyId);
}
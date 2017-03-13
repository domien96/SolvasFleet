package solvas.persistence.fleet;

import solvas.models.Fleet;
import solvas.persistence.Dao;

import java.util.Collection;

/**
 * DAO for a company.
 *
 * @author Niko Strijbol
 */
public interface FleetDao extends Dao<Fleet> {

    /**
     * Find all companies with a certain name.
     *
     * @param companyId The id of the company.
     *
     * @return The companies.
     */
    Collection<Fleet> withCompanyId(int companyId);
}
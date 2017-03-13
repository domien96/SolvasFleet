package solvas.persistence.api.dao;

import solvas.models.Fleet;
import solvas.persistence.api.Dao;

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
     * @param name The name.
     *
     * @return The companies.
     */
    Collection<Fleet> withCompanyId(int companyId);
}
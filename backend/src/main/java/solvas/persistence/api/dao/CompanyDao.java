package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.Company;
import solvas.persistence.api.Dao;

import java.util.Collection;

/**
 * DAO for companies.
 *
 * @author Niko Strijbol
 */
@Repository
public interface CompanyDao extends Dao<Company> {

    /**
     * Find all companies with a certain name.
     *
     * @param name The name.
     *
     * @return The companies.
     */
    Collection<Company> findByName(String name);
}
package solvas.persistence.company;

import solvas.models.Company;
import solvas.persistence.Dao;

import java.util.Collection;

/**
 * DAO for a company.
 *
 * @author Niko Strijbol
 */
public interface CompanyDao extends Dao<Company> {

    /**
     * Find all companies with a certain name.
     *
     * @param name The name.
     *
     * @return The companies.
     */
    Collection<Company> withName(String name);
}
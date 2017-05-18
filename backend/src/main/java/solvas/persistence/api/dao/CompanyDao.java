package solvas.persistence.api.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import solvas.persistence.api.Filter;
import solvas.service.models.Company;
import solvas.persistence.api.Dao;

import java.util.Collection;
import java.util.Optional;

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

    Page<Company> findByIdIn(Pageable pagination, Collection<Integer> ids);

    /**
     * Find the company with a certain vat number.
     *
     * @param vatNumber the vat number
     *
     * @return The company
     */
    Optional<Company> findByVatNumber(String vatNumber);
}
package solvas.persistence.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Company;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.hibernate.HibernateDao;


import java.util.Collection;

/**
 * Hibernate implementation for Company.
 *
 * @author Niko Strijbol
 */
@Repository
@Transactional
public class HibernateCompanyDao extends HibernateDao<Company> implements CompanyDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateCompanyDao() {
        super(Company.class);
    }

    @Override
    public Collection<Company> withName(String name) {
        return findAll(Filter.predicate(
                (b, r) -> b.equal(r.get("name"), name)
        ));
    }
}
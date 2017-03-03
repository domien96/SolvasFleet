package solvas.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * DAO implementation for use with Hibernate.
 */
@Repository
@Transactional
public class HibernateDao implements Dao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * @return The database session. This is managed by Spring, so no need to close.
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public <T> T save(T model) {
        run(Query.empty(s -> s.saveOrUpdate(model)));
        return model;
    }

    @Override
    public <T> T destroy(T model) {
        run(Query.empty(s -> s.delete(model)));
        return model;
    }

    @Override
    public <T> T find(Class<T> clazz, int id) {
        return run(s -> s.get(clazz, id));
    }

    @Override
    public <T> Collection<T> findAll(Class<T> clazz) {
        return run(s -> {
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            criteriaQuery.select(root);
            return s.createQuery(criteriaQuery).getResultList();
        });
    }

    /**
     * Run a query.
     *
     * @param query The query to run.
     *
     * @return The result of the query.
     */
    private <T> T run(Query<T> query) {
        return query.run(getSession());
    }
}
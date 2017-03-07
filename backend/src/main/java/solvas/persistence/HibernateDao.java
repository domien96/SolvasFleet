package solvas.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * DAO implementation for use with Hibernate.
 * @param <T> Model 
 */
@Repository
@Transactional
public abstract class HibernateDao<T extends Model> implements Dao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> clazz;

    protected HibernateDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * @return The database session. This is managed by Spring, so no need to close.
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T create(T model) {
        run(s -> s.save(model));
        return model;
    }

    public T update(T model) {
        find(model.getId()); // Make sure entity exists
        run(Query.empty(s -> s.update(s.merge(model))));
        return model;
    }

    @Override
    public T destroy(T model) {
        run(Query.empty(s -> s.delete(model)));
        return model;
    }

    @Override
    public T find(int id) {
        T result = run(s -> s.get(clazz, id));
        if(result == null) {
            throw new EntityNotFoundException();
        }
        return result;
    }

    @Override
    public Collection<T> findAll() {
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
    protected <R> R run(Query<R> query) {
        return query.run(getSession());
    }
}
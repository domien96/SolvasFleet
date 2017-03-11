package solvas.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Model;
import solvas.rest.query.Filterable;
import solvas.rest.query.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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
    public T save(T model) {
        Session s = getSession();
        if(model.getId() != 0) { // Update entity with this id
            find(model.getId()); // Make sure entity exists
            s.update(s.merge(model));
        } else { // New entity
            s.save(model);
        }
        return model;
    }

    @Override
    public T destroy(T model) {
        getSession().delete(model);
        return model;
    }

    @Override
    public T find(int id) {
        T result = getSession().get(clazz, id);
        if(result == null) {
            throw new EntityNotFoundException();
        }
        return result;
    }

    @Override
    public Collection<T> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Collection<T> findAll(Pageable pageable, Filterable<T> filters) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);

        // Apply filters
        Collection<Predicate> predicates = filters.asPredicates(builder, root);
        Predicate[] array = new Predicate[predicates.size()];
        criteriaQuery.select(root).where(predicates.toArray(array));

        // Apply pagination
        int start = pageable.getLimit() * pageable.getPage();

        return getSession().createQuery(criteriaQuery)
                .setFirstResult(start)
                .setMaxResults(pageable.getLimit())
                .getResultList();
    }

    @Override
    public long count(Filterable<T> filters) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(clazz);

        // Apply filters
        Collection<Predicate> predicates = filters.asPredicates(builder, root);
        Predicate[] array = new Predicate[predicates.size()];
        criteriaQuery.select(builder.count(root)).where(predicates.toArray(array));

        return getSession().createQuery(criteriaQuery)
                .getSingleResult();
    }
}
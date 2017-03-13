package solvas.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import solvas.models.Model;
import solvas.persistence.api.Filter;
import solvas.persistence.api.Dao;
import solvas.persistence.api.EntityNotFoundException;

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
//@Transactional -> lazy fetch
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
        Session s = getSession();
        s.save(model);
        return model;
    }

    @Override
    public T update(T model) {
        Session s = getSession();
        find(model.getId()); // Make sure entity exists
        s.update(s.merge(model));
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

    /**
     * Find all models that conform to predicates.
     *
     * @param filters The predicates.
     *
     * @return The models.
     */
    public Collection<T> findAll(Filter<T> filters) {
        return getSession().createQuery(filterQuery(filters))
                .getResultList();
    }

    /**
     * Construct a criteria query from a filterable.
     *
     * @param filters The filters.
     *
     * @return The query.
     */
    protected CriteriaQuery<T> filterQuery(Filter<T> filters) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);

        // Apply filters
        Collection<Predicate> predicates = filters.asPredicates(builder, root);
        Predicate[] array = new Predicate[predicates.size()];
        criteriaQuery.select(root).where(predicates.toArray(array));

        return criteriaQuery;
    }

    @Override
    public Collection<T> findAll(Pageable pageable, Filter<T> filters) {
        // Apply pagination
        int start = pageable.getLimit() * pageable.getPage();

        return getSession().createQuery(filterQuery(filters))
                .setFirstResult(start)
                .setMaxResults(pageable.getLimit())
                .getResultList();
    }

    @Override
    public long count(Filter<T> filters) {
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
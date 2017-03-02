package solvas.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generic DAO for basic operations.
 *
 * @author Niko Strijbol
 * @author david
 */
@Repository
@Transactional
public class Dao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * @return The database session. This is managed by Spring, so no need to close.
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save or update a model.
     *
     * @param model The model to save.
     *
     * @return The model.
     */
    public <T> T save(T model) {
        run(Query.empty(s -> s.saveOrUpdate(model)));
        return model;
    }

    /**
     * Destroy a model.
     *
     * @param model The model to destroy.

     * @return The model.
     */
    public <T> T destroy(T model) {
        run(Query.empty(s -> s.delete(model)));
        return model;
    }

    /**
     * Find a model by id.
     *
     * @param clazz The class of the model.
     *
     * @param id The ID of the model.
     *
     * @return The model.
     */
    public <T> T find(Class<T> clazz, int id) {
        return run(s -> s.get(clazz, id));
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
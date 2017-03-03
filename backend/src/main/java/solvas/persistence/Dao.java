package solvas.persistence;

import java.util.Collection;

/**
 * Generic DAO for basic operations.
 *
 * @author Niko Strijbol
 * @author david
 */
public interface Dao<T> {

    /**
     * Save or update a model.
     *
     * @param model The model to save.
     *
     * @return The model.
     */
    T save(T model);

    /**
     * Destroy a model.
     *
     * @param model The model to destroy.

     * @return The model.
     */
    T destroy(T model);

    /**
     * Find a model by id.
     *
     * @param id The ID of the model.
     *
     * @return The model.
     */
    T find(int id);

    /**
     * Find all objects of a certain type.
     *
     * @return A collection containing all objects.
     */
    Collection<T> findAll();
}
package solvas.persistence;

import java.util.Collection;

/**
 * Generic DAO for basic operations.
 *
 * @author Niko Strijbol
 * @author david
 */
public interface Dao {

    /**
     * Save or update a model.
     *
     * @param model The model to save.
     *
     * @return The model.
     */
    <T> T save(T model);

    /**
     * Destroy a model.
     *
     * @param model The model to destroy.

     * @return The model.
     */
    <T> T destroy(T model);

    /**
     * Find a model by id.
     *
     * @param clazz The class of the model.
     *
     * @param id The ID of the model.
     *
     * @return The model.
     */
    <T> T find(Class<T> clazz, int id);

    /**
     * Find all objects of a certain type.
     *
     * @param clazz The class of the objects to find.
     *
     * @return A collection containing all objects.
     */
    <T> Collection<T> findAll(Class<T> clazz);
}
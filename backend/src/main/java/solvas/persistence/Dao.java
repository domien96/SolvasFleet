package solvas.persistence;

import solvas.models.Model;
import solvas.rest.query.Filterable;
import solvas.rest.query.Pageable;

import java.util.Collection;

/**
 * Generic DAO for basic operations.
 *
 * @author Niko Strijbol
 * @author david
 * @param <T> Model of Dao
 */
public interface Dao<T extends Model> {

    /**
     * Save or update a model.
     * When the id of the model is set, the record with the same id will be updated in the database.
     * Otherwise a new record will be created, and the returned Model will have it's id set
     *
     * @param model The model to save.
     * @exception EntityNotFoundException when trying to update a non-existent record
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
     * @exception EntityNotFoundException no entity is associated with this id
     * @return The model.
     */
    T find(int id);

    /**
     * Find all objects of a certain type.
     *
     * @return A collection containing all objects.
     */
    Collection<T> findAll();

    /**
     * Find all objects, filtered by the given filter.
     *
     * @param pageable Pagination information.
     * @param filters The filter.
     *
     * @return The filtered items.
     */
    Collection<T> findAll(Pageable pageable, Filterable<T> filters);

    /**
     * Count the total number of items managed by this dao, respecting the given filter.
     *
     * @param filters The filters to use.
     *
     * @return The number of items.
     */
    long count(Filterable<T> filters);
}
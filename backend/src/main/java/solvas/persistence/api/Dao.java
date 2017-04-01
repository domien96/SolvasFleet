package solvas.persistence.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import solvas.service.models.Model;

/**
 * Interface to simplify repository creations.
 *
 * @param <T> The model for this DAO.
 *
 * @author Niko Strijbol
 * @author david
 */
@NoRepositoryBean
public interface Dao<T extends Model> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {

    /**
     * Find a model given it's ID.
     *
     * @param id The ID of the model.
     *
     * @return The model.
     *
     * @throws EntityNotFoundException If the model was not found.
     */
    default T find(int id) throws EntityNotFoundException {
        T data = findOne(id);
        if (data == null) {
            throw new EntityNotFoundException();
        }
        return data;
    }

    /**
     * Destroy a given model. The model instance is returned, but will no longer be in the database.
     *
     * @param entity The model to destroy.
     *
     * @return The destroyed model.
     *
     * @throws EntityNotFoundException If the ID of the model does not exist in the database.
     */
    default T destroy(T entity) throws EntityNotFoundException {
        T data = find(entity.getId());
        delete(data);
        return data;
    }

    /**
     * Destroy a model given it's ID. The model is retrieved from the database, destroyed and returned.
     *
     * @param id The ID of the model.
     *
     * @return The model.
     *
     * @throws EntityNotFoundException If the model was not found.
     */
    default T destroy(int id) throws EntityNotFoundException {
        T data = find(id);
        delete(id);
        return data;
    }

    /**
     * Archive a model given it's ID.
     *
     * @param id The ID of the model.
     *
     * @return The model.
     *
     * @throws EntityNotFoundException If the model was not found.
     */
    default T archive(int id) throws EntityNotFoundException {
        T data = find(id);
        data.setArchived(true);
        save(data);
        return data;
    }

    /**
     * Archive a given model.
     *
     * @param entity The model to archive.
     *
     * @return The model.
     *
     * @throws EntityNotFoundException If the model was not found.
     */
    default T archive(T entity) throws EntityNotFoundException {
        T data = find(entity.getId());
        data.setArchived(true);
        save(data);
        return data;
    }
}
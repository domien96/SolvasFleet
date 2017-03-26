package solvas.persistence.api;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import solvas.models.Model;

/**
 * Interface to simplify repository creations.
 *
 * @param <T> The model for this DAO.
 *
 * @author Niko Strijbol
 * @author david
 */
@NoRepositoryBean
public interface Dao<T extends Model> extends PagingAndSortingRepository<T, Integer>, JpaSpecificationExecutor<T> {

    default T find(int id) throws EntityNotFoundException {
        T data = findOne(id);
        if (data == null) {
            throw new EntityNotFoundException();
        }
        return data;
    }

    default T destroy(T entity) throws EntityNotFoundException {
        T data = find(entity.getId());
        delete(data);
        return data;
    }

    default T destroy(int id) throws EntityNotFoundException {
        T data = find(id);
        delete(id);
        return data;
    }
}
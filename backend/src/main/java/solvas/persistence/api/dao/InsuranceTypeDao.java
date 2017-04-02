package solvas.persistence.api.dao;


import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.InsuranceType;

import java.util.Collection;

/**
 * Dao for InsuranceTypeDao
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InsuranceTypeDao extends Dao<InsuranceType> {

    /**
     * Get all models with a given type.
     *
     * TODO: we should implement constraints in the database to prevent two
     * models with the same type, so an uniqueness constraint should be applied.
     * Once that is done, this method can return one type instead of a collection.
     *
     * @param type The name of the type.
     *
     * @return Should be a collection with one type.
     */
    Collection<InsuranceType> findByName(String type);
}
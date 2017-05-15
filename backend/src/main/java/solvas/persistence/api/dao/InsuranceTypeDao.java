package solvas.persistence.api.dao;


import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.InsuranceType;

import java.util.Optional;

/**
 * Dao for InsuranceTypeDao
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InsuranceTypeDao extends Dao<InsuranceType> {

    /**
     * Get the type from it's name.
     *
     * @param type The name of the type.
     *
     * @return The insurance type.
     */
    Optional<InsuranceType> findByName(String type);
}
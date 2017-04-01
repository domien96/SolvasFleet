package solvas.persistence.api.dao;


import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.InsuranceType;

import java.util.Collection;

/**
 * DAO for InsuranceType.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InsuranceTypeDao extends Dao<InsuranceType> {

    /**
     * Get the Insurance type model with the given name.
     *
     * @param name The name of the type.
     *
     * @return Should be a collection with one type.
     */
    InsuranceType findByName(String name);
}
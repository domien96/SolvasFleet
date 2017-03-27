package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.models.Fleet;
import solvas.models.SubFleet;
import solvas.persistence.api.Dao;

import java.util.Collection;

/**
 * DAO for a sub fleet.
 *
 * @author Niko Strijbol
 * @author Steven Bastiaens
 */
@Repository
public interface SubFleetDao extends Dao<SubFleet> {

    /**
     * Find the sub fleets belonging to a given fleet.
     *
     * @param fleet The fleet to find sub fleets for. Cannot be null.
     *
     * @return The sub fleets, or an empty collection if there are none.
     */
    Collection<SubFleet> findByFleet(Fleet fleet);
}
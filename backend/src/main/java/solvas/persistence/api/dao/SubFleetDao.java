package solvas.persistence.api.dao;

import solvas.models.Fleet;
import solvas.models.SubFleet;
import solvas.persistence.api.Dao;

import java.util.Collection;

/**
 * TODO document
 */
public interface SubFleetDao extends Dao<SubFleet> {


    /**
     * TODO document
     * @param fleet
     * @return
     */
    Collection<SubFleet> findByFleet(Fleet fleet);
}
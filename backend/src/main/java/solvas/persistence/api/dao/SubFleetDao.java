package solvas.persistence.api.dao;

import solvas.models.SubFleet;
import solvas.persistence.api.Dao;

import java.util.Collection;

/**
 * TODO document
 */
public interface SubFleetDao extends Dao<SubFleet> {


    /**
     * TODO document
     * @param fleetId
     * @return
     */
    Collection<SubFleet> withFleetId(int fleetId);
}
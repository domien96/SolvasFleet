package solvas.persistence.subFleet;

import solvas.models.SubFleet;
import solvas.persistence.Dao;

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
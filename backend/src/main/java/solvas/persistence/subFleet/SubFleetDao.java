package solvas.persistence.subFleet;

import solvas.models.Company;
import solvas.models.SubFleet;
import solvas.persistence.Dao;

import java.util.Collection;

public interface SubFleetDao extends Dao<SubFleet> {


    Collection<SubFleet> withFleetId(int fleetId);
}
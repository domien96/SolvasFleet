package solvas.rest.logic;

import solvas.models.Fleet;
import solvas.models.FleetSubscription;
import solvas.models.SubFleet;
import solvas.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.FleetDao;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.persistence.api.dao.SubFleetDao;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by steve on 12/03/2017.
 */
public class LinkVehicleCompany {
    public void run(int fleetId, Vehicle vehicle, DaoContext daoContext) throws  InconsistentDbException {
        FleetSubscriptionDao fleetSubscriptionDao = daoContext.getFleetSubscriptionDao();
        SubFleetDao subFleetDao = daoContext.getSubFleetDao();
        FleetDao fleetDao = daoContext.getFleetDao();
        //Find active subscription
        FleetSubscription activeFleetSubscription=null;
        for (FleetSubscription subs: fleetSubscriptionDao.withVehicleId(vehicle.getId())){
            if (subs.getStartDate().isBefore(LocalDate.now())
                    && subs.getEndDate().isAfter(LocalDate.now())){
                subs.setEndDate(LocalDate.now());
                fleetSubscriptionDao.save(subs);
                if (activeFleetSubscription!=null){
                    throw new InconsistentDbException();
                }
                activeFleetSubscription=subs;

            }
        }
        activeFleetSubscription = new FleetSubscription();
        activeFleetSubscription.setStartDate(LocalDate.now());
        activeFleetSubscription.setVehicle(vehicle);


        // If we cannot find a fleet, we cannot make a new one, and we'll just let this
        // crash. We don't know the company, so we cannot create a fleet.
        Fleet fleet = fleetDao.find(fleetId);


        //Find appropriate sub fleet
        SubFleet subFleet=null;
        Collection<SubFleet> subFleets = subFleetDao.withFleetId(fleet.getId());
        for (SubFleet subs: subFleets){
            if (subs.getVehicleType().getId()==vehicle.getType().getId()){
                if (subFleet==null){
                    subFleet=subs;
                } else {
                    throw new InconsistentDbException();
                    //2 or more sub_fleets with same type
                }
            }
        }
        if (subFleet==null){
            //Create new subfleet
            subFleet = new SubFleet();
            subFleet.setId(0);
            subFleet.setVehicleType(vehicle.getType());
            subFleet.setFleet(fleet);
            subFleet = subFleetDao.save(subFleet);
        }

        //save active fleet subscription
        activeFleetSubscription.setSubFleet(subFleet);
        activeFleetSubscription.setId(0);
        activeFleetSubscription = fleetSubscriptionDao.save(activeFleetSubscription);

    }
}

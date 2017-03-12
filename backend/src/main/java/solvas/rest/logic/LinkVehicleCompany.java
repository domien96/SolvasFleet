package solvas.rest.logic;

import solvas.models.FleetSubscription;
import solvas.models.SubFleet;
import solvas.models.Vehicle;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;

import java.time.LocalDate;

/**
 * Created by steve on 12/03/2017.
 */
public class LinkVehicleCompany {
    public void run(int companyId, Vehicle vehicle, FleetSubscriptionDao fleetSubscriptionDao) throws InconsistentDbException {
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
        activeFleetSubscription.setEndDate(LocalDate.MAX);
        activeFleetSubscription.setStartDate(LocalDate.now());
        activeFleetSubscription.setVehicle(vehicle);

        //Find appropriate sub fleet
        SubFleet subFleet=null;




    }
}

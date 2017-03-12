package solvas.rest.logic;

import solvas.models.*;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;

import java.time.LocalDate;

/**
 * Created by steve on 11/03/2017.
 *
 */
public class VehicleToCompany {
    public Company run(Vehicle vehicle,FleetSubscriptionDao fleetSubscriptionDao) throws InconsistentDbException, NoActiveSubscriptionException {

        //Find active subscription
        FleetSubscription activeFleetSubscription=null;

        for (FleetSubscription subs: fleetSubscriptionDao.withVehicleId(vehicle.getId())){
            if (subs.getStartDate().isBefore(LocalDate.now())
                    && subs.getEndDate().isAfter(LocalDate.now())){
                if (activeFleetSubscription!=null){
                    throw new InconsistentDbException();
                }
                activeFleetSubscription=subs;
            }
        }
        if (activeFleetSubscription == null) {
            throw new NoActiveSubscriptionException();
        }

        //Find subFleet
        SubFleet subFleet =activeFleetSubscription.getSubFleet();
        if (subFleet==null) {
            throw new InconsistentDbException();
        }

        //Find Fleet
        Fleet fleet = subFleet.getFleet();
        if (fleet==null) {
            throw new InconsistentDbException();
        }

        //Find Company
        Company company = fleet.getCompany();
        if (company==null) {
            throw new InconsistentDbException();
        }
        return company;
    }
    public class NoActiveSubscriptionException extends Exception{

    }

}

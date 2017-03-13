package solvas.rest.logic;

import solvas.models.Fleet;
import solvas.models.FleetSubscription;
import solvas.models.SubFleet;
import solvas.models.Vehicle;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.subFleet.SubFleetDao;

import java.time.LocalDate;
import java.util.Collection;

/**
 * LinkVehicleCompany is a helper class part of the backend logic/service layer. This specific class helps when
 * inserting a vehicle. Since our model(db) is different than the model of the Api, a conversion is required.
 * A vehicle has no direct relation with a fleet, so one must be build/found. This class will check/create a
 * route from a vehicle to a fleet.
 *
 * Todo rename Company-> fleet
 * Todo find a better way to model logic
 *
 *
 * @author sjabasti
 * @author nistrijb
 */
public class LinkVehicleCompany {
    /**
     * This will create a link or correct a link between vehicles and fleets
     * @param fleetId destination fleet
     * @param vehicle start vehicle
     * @param fleetSubscriptionDao dao needed for this complex operation
     * @param subFleetDao dao needed for this complex operation
     * @param fleetDao dao needed for this complex operation
     * @param companyDao dao needed for this complex operation
     * @throws InconsistentDbException any inconsistencies in the database will result in this error
     *
     * Todo milestone 2, refactor for updating
     */
    public void run(int fleetId, Vehicle vehicle, FleetSubscriptionDao fleetSubscriptionDao
            , SubFleetDao subFleetDao, FleetDao fleetDao, CompanyDao companyDao) throws  InconsistentDbException {
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

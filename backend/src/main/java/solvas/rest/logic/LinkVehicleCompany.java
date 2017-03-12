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
 * Created by steve on 12/03/2017.
 */
public class LinkVehicleCompany {
    public void run(int companyId, Vehicle vehicle, FleetSubscriptionDao fleetSubscriptionDao
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
        activeFleetSubscription.setEndDate(LocalDate.now().plusYears(10));
        activeFleetSubscription.setStartDate(LocalDate.now());
        activeFleetSubscription.setVehicle(vehicle);

        //Search Fleet
        Fleet fleet = null;
        Collection<Fleet> fleetsWithCompanyId = fleetDao.withCompanyId(companyId);
        for(Fleet fl: fleetsWithCompanyId){
            if (fleet==null){
                fleet=fl;
            } else {
                throw new InconsistentDbException();
                //Fleets is not yet implemented in the api
            }
        }
        if (fleet==null){
            //Create new default fleet
            fleet = new Fleet();
            fleet.setId(0);
            fleet.setCompany(companyDao.find(companyId));
            fleet.setName("default");
            fleet=fleetDao.save(fleet);

        }


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

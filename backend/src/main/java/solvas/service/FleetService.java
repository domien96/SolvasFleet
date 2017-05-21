package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.query.VehicleFilter;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.mappers.ContractMapper;
import solvas.service.models.*;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.FleetMapper;
import solvas.rest.api.models.ApiFleet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Fleetservice class
 */
@Service
public class FleetService extends AbstractService<Fleet,ApiFleet> {


    /**
     * Construct a FleetService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiFleet and Fleet
     */
    @Autowired
    public FleetService(DaoContext context, FleetMapper mapper) {
        super(context.getFleetDao(),context, mapper);
    }


    @Autowired
    private VehicleService vehicleService;

    @Override
    public void archive(int id) throws EntityNotFoundException, UnarchivableException {
        Fleet fleet = context.getFleetDao().find(id);

        //Stop all active fleet subscriptions
        Collection<FleetSubscription> fleetSubscriptions
                = context.getFleetSubscriptionDao().findByFleetAndEndDateIsNull(fleet);

        //Stop all active fleet subscriptions
        for (FleetSubscription subs:fleetSubscriptions){
            vehicleService.archive(subs.getVehicle().getId());
        }


        super.archive(id);
    }
}

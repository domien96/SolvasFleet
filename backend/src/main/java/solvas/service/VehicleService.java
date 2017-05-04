package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.VehicleTypeDao;
import solvas.service.mappers.ContractMapper;
import solvas.service.models.*;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.VehicleMapper;
import solvas.rest.api.models.ApiVehicle;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * VehicleService Class
 */
@Service
public class VehicleService extends AbstractService<Vehicle,ApiVehicle>{

    private final VehicleTypeDao vehicleTypesDao;

    /**
     * Construct a VehicleService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiVehicle and Vehicle
     */
    @Autowired
    public VehicleService(DaoContext context, VehicleMapper mapper) {
        super(context.getVehicleDao(), mapper);
        this.vehicleTypesDao = context.getVehicleTypeDao();
    }

    /**
     * Finds all types of vehicles in the database
     * @return types of vehicles
     */
    public Collection<String> findAllVehicleTypes() {
        return vehicleTypesDao.findAll().stream().map(VehicleType::getName).collect(Collectors.toSet());

    }


    @Override
    public void archive(int id) throws EntityNotFoundException {
        Vehicle vehicle = context.getVehicleDao().find(id);

        //Stop all active fleet subscriptions
        Optional<FleetSubscription> fleetSubscription
                = context.getFleetSubscriptionDao().findByVehicleAndEndDateIsNull(vehicle);

        final LocalDateTime endDate = LocalDateTime.now();

        if (fleetSubscription.isPresent()){
            fleetSubscription.get().setEndDate(endDate.toLocalDate());
            context.getFleetSubscriptionDao().save(fleetSubscription.get());
            //Archive active contracts
            Collection<Contract> contracts
                    = context.getContractDao().findByFleetSubscription(fleetSubscription.get());
            contracts.stream()
                    .filter(c->c.getEndDate()!=null && c.getEndDate().isAfter(endDate))
                    .forEach(c->{
                        c.setEndDate(endDate);
                        context.getContractDao().save(c);
                    });
        }

        super.archive(id);
    }
}

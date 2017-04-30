package filters;

import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.VehicleFilter;
import solvas.service.models.Vehicle;

/**
 * All tests that involve the VehicleFilter
 */
public class VehicleFilterTest extends AbstractFilterTest<Vehicle>{

    private VehicleFilter vehicleFilter=new VehicleFilter();


    @Override
    ArchiveFilter<Vehicle> getFilterWithCorrectParameters() {
        vehicleFilter.setFleet(10);
        vehicleFilter.setLeasingCompany(10);
        vehicleFilter.setType("random");
        vehicleFilter.setLicensePlate("plate");
        vehicleFilter.setYear(1990);
        vehicleFilter.setVin("vin");
        return vehicleFilter;
    }

    @Override
    ArchiveFilter<Vehicle> getFilterWithBadParameters() {
        return vehicleFilter;
    }

    @Override
    int parameterSize() {
        return 7;
    }
}

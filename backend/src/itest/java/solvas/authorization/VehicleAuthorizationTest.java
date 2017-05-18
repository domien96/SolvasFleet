package solvas.authorization;

import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiVehicle;

import java.time.LocalDateTime;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

/**
 * Test Vehicle routes authorization
 */
public class VehicleAuthorizationTest extends AbstractAuthorizationTest {

    @Override
    public String getUrl() {
        return RestTestFixtures.VEHICLE_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.VEHICLE_ID_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiVehicle vehicle = random(ApiVehicle.class);
        vehicle.setMileage(100);
        vehicle.setId(1);
        vehicle.setFleet(1);
        vehicle.setYear(LocalDateTime.now().withYear(1990));
        vehicle.setLeasingCompany(1);
        vehicle.setValue(1000);
        vehicle.setType("PersonalVehicle");
        vehicle.setVin("WDDGF8BB6CR236880");
        return vehicle;
    }
}

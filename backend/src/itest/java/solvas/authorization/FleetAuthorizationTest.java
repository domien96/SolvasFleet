package solvas.authorization;

import org.junit.Test;
import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiFleet;
import solvas.rest.api.models.ApiModel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static io.github.benas.randombeans.api.EnhancedRandom.random;

public class FleetAuthorizationTest extends AbstractAuthorizationTest {

    @Override
    public String getUrl() {
        return RestTestFixtures.FLEET_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.FLEET_ID_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiFleet fleet = random(ApiFleet.class);
        fleet.setId(1);
        fleet.setFacturationPeriod(2);
        fleet.setPaymentPeriod(2);
        fleet.setCompany(3);
        return fleet;
    }

    @Test
    public void canReadVehicles() throws Exception {
        getMockMvc().perform(auth(get(RestTestFixtures.VEHICLES_FROM_FLEET_URL),adminToken)).andExpect(status().isOk());
    }

    @Test
    public void cantReadVehicles() throws Exception {
        getMockMvc().perform(auth(get(RestTestFixtures.VEHICLES_FROM_FLEET_URL),nopermissionToken)).andExpect(status().isForbidden());
    }

    @Test
    public void canManageVehicles() throws Exception {

    }
}

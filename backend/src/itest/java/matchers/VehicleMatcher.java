package matchers;

import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Vehicle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class VehicleMatcher implements TestMatcher<Vehicle> {
    @Override
    public void jsonMatcher(ResultActions res, Vehicle vehicle) throws Exception {
        res.andExpect(jsonPath("id").value(vehicle.getId()));
        res.andExpect(jsonPath("url").value(vehicle.getUrl()));
        res.andExpect(jsonPath("licensePlate").value(vehicle.getLicensePlate()));
        res.andExpect(jsonPath("value").value(vehicle.getValue()));
        res.andExpect(jsonPath("chassisNumber").value(vehicle.getChassisNumber()));
        res.andExpect(jsonPath("kilometerCount").value(vehicle.getKilometerCount()));

        res.andExpect(jsonPath("model").value(vehicle.getModel()));
        res.andExpect(jsonPath("year").value(vehicle.getYear()));

        /*res.andExpect(jsonPath("leasingCompany").value(vehicle.getLeasingCompany()));
               res.andExpect(jsonPath("type").value(vehicle.getType()));
         res.andExpect(jsonPath("company").value(vehicle.getCompany())); invalid match
        */
    }

    @Override
    public void performAsserts(Vehicle actual, Vehicle expected) {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));
        assertThat(actual.getLicensePlate(),is(equalTo(expected.getLicensePlate())));
        assertThat(actual.getValue(),is(equalTo(expected.getValue())));
        assertThat(actual.getChassisNumber(),is(equalTo(expected.getChassisNumber())));
        assertThat(actual.getKilometerCount(),is(equalTo(expected.getKilometerCount())));
        assertThat(actual.getModel(),is(equalTo(expected.getModel())));
        assertThat(actual.getYear(),is(equalTo(expected.getYear())));

        /*
        assertThat(actual.getType(),is(equalTo(expected.getType())));
        assertThat(actual.getCompany(),is(equalTo(expected.getCompany())));
        assertThat(actual.getLeasingCompany(),is(equalTo(expected.getLeasingCompany())));invalid match*/
    }
}

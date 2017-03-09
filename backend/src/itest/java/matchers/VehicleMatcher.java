package matchers;

import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Vehicle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/***
 * Vehicle matcher for checking:
 * - That the json presented by spring contains the corrects attributes of the  object
 * - That the body of a request won't change when saving it to a database using the DAO-objects
 */
public class VehicleMatcher implements TestMatcher<Vehicle> {

    /**
     * Method that checks the result of the json string that is contained in the HTTP request
     * @param res from MockMVC object
     * @param vehicle vehicle object that should be equal to the object in json representation
     * @throws Exception when mockMVC fails to perform the action or jsonPath fails to retrieve the attribute
     */
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

    /**
     * Method that checks (with assertions) that the attributes of both vehicles are equal
     * This way we can separate the assertions easily
     * @param actual the actual vehicle
     * @param expected the expected vehicle
     * */
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

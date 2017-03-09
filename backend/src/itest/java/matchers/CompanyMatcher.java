package matchers;

import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Company;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/***
 * Company matcher for checking:
 * - That the json presented by spring contains the corrects attributes of the  object
 * - That the body of a request won't change when saving it to a database using the DAO-objects
 */
public class CompanyMatcher implements TestMatcher<Company>{

    /**
     * Method that checks the result of the json string that is contained in the HTTP request
     * @param resultActions from MockMVC object
     * @param source Company object that should be equal to the object in json representation
     * @throws Exception when mockMVC fails to perform the action or jsonPath fails to retrieve the attribute
     */
    public void jsonMatcher(ResultActions resultActions,Company source) throws Exception {
        resultActions.andExpect(jsonPath("id").value(source.getId()));
        resultActions.andExpect(jsonPath("address").value(source.getAddress()));
        resultActions.andExpect(jsonPath("name").value(source.getName()));
        resultActions.andExpect(jsonPath("phoneNumber").value(source.getPhoneNumber()));
        resultActions.andExpect(jsonPath("vatNumber").value(source.getVatNumber()));
        resultActions.andExpect(jsonPath("url").value(source.getUrl()));
    }


    /**
     * Method that checks (with assertions) that the attributes of both companies are equal
     * This way we can separate the assertions easily
     * @param actual the actual company
     * @param expected the expected company
     */
    public void performAsserts(Company actual,Company expected) {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getName(),is(equalTo(expected.getName())));
        assertThat(actual.getPhoneNumber(),is(equalTo(expected.getPhoneNumber())));
        assertThat(actual.getAddress(),is(equalTo(expected.getAddress())));
        assertThat(actual.getVatNumber(),is(equalTo(expected.getVatNumber())));
        assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));
    }


}

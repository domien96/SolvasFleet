package matchers;

import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Role;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/***
 * Role matcher for checking:
 * - That the json presented by spring contains the corrects attributes of the  object
 * - That the body of a request won't change when saving it to a database using the DAO-objects
 */
public class RoleMatcher implements TestMatcher<Role> {

    /**
     * Method that checks the result of the json string that is contained in the HTTP request
     * @param res from MockMVC object
     * @param role role object that should be equal to the object in json representation
     * @throws Exception when mockMVC fails to perform the action or jsonPath fails to retrieve the attribute
     */
    @Override
    public void jsonMatcher(ResultActions res,Role role) throws Exception {
        res.andExpect(jsonPath("id").value(role.getId()));
        res.andExpect(jsonPath("function").value(role.getFunction()));
        res.andExpect(jsonPath("url").value(role.getUrl()));
/*       res.andExpect(jsonPath("endDate").value(role.getEndDate()));
        res.andExpect(jsonPath("startDate").value(role.getStartDate()));
        /*res.andExpect(jsonPath("company").value(role.getCompany()));
        res.andExpect(jsonPath("user").value(role.getUser())); invalid match*/
    }

    /**
     * Method that checks (with assertions) that the attributes of both roles are equal
     * This way we can separate the assertions easily
     * @param actual the actual roles
     * @param expected the expected role
     */
    @Override
    public void performAsserts(Role actual, Role expected) {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getFunction(),is(equalTo(expected.getFunction())));
        assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));

        /*assertThat(actual.getEndDate(),is(equalTo(expected.getEndDate())));
        assertThat(actual.getStartDate(),is(equalTo(expected.getStartDate())));

        /*
        assertThat(actual.getUser(),is(equalTo(expected.getUser())));
        assertThat(actual.getCompany(),is(equalTo(expected.getCompany())));invalid match*/
    }
}

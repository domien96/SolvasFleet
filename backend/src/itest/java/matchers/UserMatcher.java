package matchers;

import org.springframework.test.web.servlet.ResultActions;
import solvas.models.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/***
 * User matcher for checking:
 * - That the json presented by spring contains the corrects attributes of the  object
 * - That the body of a request won't change when saving it to a database using the DAO-objects
 */
public class UserMatcher implements TestMatcher<User>{

    /**
     * Method that checks the result of the json string that is contained in the HTTP request
     * @param res from MockMVC object
     * @param user User object that should be equal to the object in json representation
     * @throws Exception when mockMVC fails to perform the action or jsonPath fails to retrieve the attribute
     */
    @Override
    public void jsonMatcher(ResultActions res, User user) throws Exception {
        res.andExpect(jsonPath("id").value(user.getId()));
        res.andExpect(jsonPath("firstName").value(user.getFirstName()));
        res.andExpect(jsonPath("lastName").value(user.getLastName()));
        res.andExpect(jsonPath("email").value(user.getEmail()));
        res.andExpect(jsonPath("url").value(user.getUrl()));
        res.andExpect(jsonPath("password").value(user.getPassword()));
    }

    /**
     * Method that checks (with assertions) that the attributes of both users are equal
     * This way we can separate the assertions easily
     * @param actual the actual user
     * @param expected the expected user
     */
    @Override
    public void performAsserts(User actual, User expected) {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getFirstName(),is(equalTo(expected.getFirstName())));
        assertThat(actual.getLastName(),is(equalTo(expected.getLastName())));
        assertThat(actual.getEmail(),is(equalTo(expected.getEmail())));
        //assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));
        assertThat(actual.getPassword(),is(equalTo(expected.getPassword())));
    }
}

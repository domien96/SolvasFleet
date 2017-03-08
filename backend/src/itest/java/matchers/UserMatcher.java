package matchers;

import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Role;
import solvas.models.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class UserMatcher implements TestMatcher<User>{
    @Override
    public void jsonMatcher(ResultActions res, User user) throws Exception {
        res.andExpect(jsonPath("id").value(user.getId()));
        res.andExpect(jsonPath("firstName").value(user.getFirstName()));
        res.andExpect(jsonPath("lastName").value(user.getLastName()));
        res.andExpect(jsonPath("email").value(user.getEmail()));
        res.andExpect(jsonPath("url").value(user.getUrl()));
        res.andExpect(jsonPath("password").value(user.getPassword()));
    }

    @Override
    public void performAsserts(User actual, User expected) {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getFirstName(),is(equalTo(expected.getFirstName())));
        assertThat(actual.getLastName(),is(equalTo(expected.getLastName())));
        assertThat(actual.getEmail(),is(equalTo(expected.getEmail())));
        assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));
        assertThat(actual.getPassword(),is(equalTo(expected.getPassword())));
    }
}

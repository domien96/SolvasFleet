package matchers;

import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Role;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class RoleMatcher implements TestMatcher<Role> {

    @Override
    public void jsonMatcher(ResultActions res,Role role) throws Exception {
        res.andExpect(jsonPath("id").value(role.getId()));
        res.andExpect(jsonPath("function").value(role.getFunction()));
        res.andExpect(jsonPath("url").value(role.getUrl()));
        res.andExpect(jsonPath("endDate").value(role.getEndDate()));
        res.andExpect(jsonPath("startDate").value(role.getStartDate()));
        /*res.andExpect(jsonPath("company").value(role.getCompany()));
        res.andExpect(jsonPath("user").value(role.getUser())); invalid match*/
    }

    @Override
    public void performAsserts(Role actual, Role expected) {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getFunction(),is(equalTo(expected.getFunction())));
        assertThat(actual.getEndDate(),is(equalTo(expected.getEndDate())));
        assertThat(actual.getStartDate(),is(equalTo(expected.getStartDate())));
        assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));

        /*
        assertThat(actual.getUser(),is(equalTo(expected.getUser())));
        assertThat(actual.getCompany(),is(equalTo(expected.getCompany())));invalid match*/
    }
}

package matchers;

import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Company;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CompanyMatcher implements TestMatcher<Company>{


    /*Matches the following variables
    id
    address
    name
    phoneNumber
    vatNumber
    url
     */


    public void jsonMatcher(ResultActions resultActions,Company source) throws Exception {
        resultActions.andExpect(jsonPath("id").value(source.getId()));
        resultActions.andExpect(jsonPath("address").value(source.getAddress()));
        resultActions.andExpect(jsonPath("name").value(source.getName()));
        resultActions.andExpect(jsonPath("phoneNumber").value(source.getPhoneNumber()));
        resultActions.andExpect(jsonPath("vatNumber").value(source.getVatNumber()));
        resultActions.andExpect(jsonPath("url").value(source.getUrl()));
    }





    public void performAsserts(Company actual,Company expected) {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getName(),is(equalTo(expected.getName())));
        assertThat(actual.getPhoneNumber(),is(equalTo(expected.getPhoneNumber())));
        assertThat(actual.getAddress(),is(equalTo(expected.getAddress())));
        assertThat(actual.getVatNumber(),is(equalTo(expected.getVatNumber())));
        assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));
    }


}

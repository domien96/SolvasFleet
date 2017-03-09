package matchers;

import org.springframework.test.web.servlet.ResultActions;

/***
 * Matcher interface for checking:
 * - That the json presented by spring contains the corrects attributes of the  object
 * - That the body of a request wont change when saving it to a database using the DAO-objects
 */
public interface TestMatcher<T> {

    /**
     * Method that checks the result of the json string that is contained in the HTTP request
     * @param res from MockMVC object
     * @param source Company object that should be equal to the object in json representation
     * @throws Exception when mockMVC fails to perform the action or jsonPath fails to retrieve the attribute
     */
      void jsonMatcher(ResultActions res,T source) throws Exception;


    /**
     * Method that checks (with assertions) that the attributes of both objects are equal
     * This way we can separate the assertions easily
     * @param actual the actual object
     * @param expected the expected object
     */
    void performAsserts(T actual, T expected);
}

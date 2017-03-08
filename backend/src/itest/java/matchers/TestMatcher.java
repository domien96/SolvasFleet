package matchers;

import org.springframework.test.web.servlet.ResultActions;

/***
 * Matching class for checking:
 * - That the json presented by spring contains the corrects attributes of the  object
 * - That the body of a request wont change when saving it to a database using the DAO-objects
 * @param <T>
 */
public interface TestMatcher<T> {

    public  void jsonMatcher(ResultActions res,T t1) throws Exception;

    public  void performAsserts(T t1, T t2);
}

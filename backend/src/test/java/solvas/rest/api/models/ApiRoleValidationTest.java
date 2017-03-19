package solvas.rest.api.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import solvas.TestUtils;
import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.api.dao.UserDao;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Niko Strijbol
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Configuration
@SuppressWarnings("squid:S1192")
public class ApiRoleValidationTest {

    @Configuration
    static class ContextConfiguration extends ValidatorConfiguration {

        @Bean
        public CompanyDao companyDao() {
            return TestUtils.mockedCompanyDao();
        }

        @Bean
        public UserDao userDao() {
            return TestUtils.mockedUserDao();
        }
    }

    @Autowired
    private Validator validator;


    @Test
    public void testValid() {
        ApiRole role = new ApiRole();
        role.setFunction("TEST");
        role.setCompany(TestUtils.VALID_COMPANY);
        role.setUser(TestUtils.VALID_USER);
        role.setStartDate(LocalDateTime.now());
        assertEquals(0, validator.validate(role).size());
    }

    @Test
    public void testEmptyAndNoFunction() {
        final String function = "function";
        ApiRole role = new ApiRole();
        role.setCompany(TestUtils.VALID_COMPANY);
        role.setUser(TestUtils.VALID_USER);
        role.setStartDate(LocalDateTime.now());
        role.setFunction("");
        Set<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(function, v.iterator().next().getPropertyPath().iterator().next().getName());
        role.setFunction(null);
        v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(function, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    @Test
    public void testNullStartDate() {
        ApiRole role = new ApiRole();
        role.setCompany(TestUtils.VALID_COMPANY);
        role.setUser(TestUtils.VALID_USER);
        role.setFunction("random");
        Set<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals("startDate", v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    @Test
    public void testCompanyAndUser() {
        ApiRole role = new ApiRole();
        role.setStartDate(LocalDateTime.now());
        role.setFunction("admin");

        Set<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(2, v.size());

        role.setUser(TestUtils.VALID_USER);
        v = validator.validate(role);
        assertEquals("company", v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    @Test
    public void testDates() {
        final String startField = "startDate";
        final String endField = "endDate";
        ApiRole role = new ApiRole();
        role.setCompany(TestUtils.VALID_COMPANY);
        role.setUser(TestUtils.VALID_USER);
        role.setFunction("user");
        LocalDateTime now = LocalDateTime.now();
        role.setStartDate(now);

        role.setEndDate(now.plusYears(5));
        assertEquals(0, validator.validate(role).size());

        role.setEndDate(now);
        assertEquals(0, validator.validate(role).size());

        role.setEndDate(now.minusYears(5));
        Collection<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(2, validator.validate(role).size());

        for (ConstraintViolation<ApiRole> next: v){
            assertTrue(next.getPropertyPath().iterator().next().getName().equals(startField) ||
                    next.getPropertyPath().iterator().next().getName().equals(endField)
            );
        }
    }
}
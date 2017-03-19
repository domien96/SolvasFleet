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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

/**
 * @author Niko Strijbol
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Configuration
@SuppressWarnings({"squid:UndocumentedApi", "squid:S109"})
public class ApiFleetValidationTest {

    @Configuration
    @SuppressWarnings("squid:UndocumentedApi")
    static class ContextConfiguration extends ValidatorConfiguration {
        @Bean
        public CompanyDao dao() {
            return TestUtils.mockedCompanyDao();
        }
    }

    @Autowired
    private Validator validator;

    @Test
    public void testValid() {
        ApiFleet fleet = random(ApiFleet.class);
        fleet.setCompany(TestUtils.VALID_COMPANY);
        assertEquals(0, validator.validate(fleet).size());
    }

    @Test
    public void testInvalidCompany() {
        final String company = "company";
        ApiFleet fleet = random(ApiFleet.class, company);
        fleet.setCompany(630);
        Set<ConstraintViolation<ApiFleet>> v = validator.validate(fleet);
        assertEquals(1, v.size());
        assertEquals(company, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    @Test
    public void testNoneAndEmpty() {
        ApiFleet fleet = new ApiFleet();
        assertEquals(2, validator.validate(fleet).size());
        fleet.setCompany(TestUtils.VALID_COMPANY);
        fleet.setName("");
        assertEquals(1, validator.validate(fleet).size());
    }
}
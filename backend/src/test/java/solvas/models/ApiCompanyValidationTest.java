package solvas.models;

import org.junit.Test;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

/**
 * Test that {@link ApiCompany}'s validations work.
 *
 * @author Niko Strijbol
 */
@SuppressWarnings({"squid:UndocumentedApi", "squid:S109"})
public class ApiCompanyValidationTest extends ValidationTest {

    @Test
    public void testValid() {
        ApiCompany company = random(ApiCompany.class);
        company.setPhoneNumber("+32 56 22 56 00");
        assertEquals(0, validator.validate(company).size());
    }

    @Test
    public void testInvalidAddress() {
        ApiCompany company = random(ApiCompany.class);
        company.setPhoneNumber("+32 56 22 56 22");
        company.setAddress(null);
        assertEquals(1, validator.validate(company).size());

        ApiAddress emptyAddress = new ApiAddress();
        company.setAddress(emptyAddress);
        assertEquals(5, validator.validate(company).size());
    }

    @Test
    public void testInvalidPhone() {
        final String phoneNumber = "phoneNumber";
        ApiCompany company = random(ApiCompany.class, phoneNumber);
        company.setPhoneNumber("TESTTESTTESTTEST");
        Set<ConstraintViolation<ApiCompany>> v = validator.validate(company);
        assertEquals(1, v.size());
        assertEquals(phoneNumber, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    @Test
    public void testNone() {
        ApiCompany company = new ApiCompany();
        assertEquals(4, validator.validate(company).size());
    }

    @Test
    public void testEmpty() {
        ApiCompany company = random(ApiCompany.class);
        company.setName("");
        company.setPhoneNumber("");
        company.setVatNumber("");
        assertEquals(3, validator.validate(company).size());
    }
}
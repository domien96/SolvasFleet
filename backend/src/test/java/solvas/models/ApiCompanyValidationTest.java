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
public class ApiCompanyValidationTest extends ValidationTest {

    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiCompany company = random(ApiCompany.class);
        company.setPhoneNumber("+32 56 22 56 00");
        assertEquals(0, validator.validate(company).size());
    }

    /**
     * Test a company that has an invalid address.
     */
    @Test
    public void testInvalidAddress() {

        ApiCompany company = random(ApiCompany.class);
        company.setPhoneNumber("+32 56 22 56 22");
        company.setAddress(null);
        assertEquals(1, validator.validate(company).size());

        ApiAddress emptyAddress = new ApiAddress();

        // Get how many errors the address produces
        int addressErrors = validator.validate(emptyAddress).size();

        company.setAddress(emptyAddress);
        assertEquals(addressErrors, validator.validate(company).size());
    }

    /**
     * Test a company with an invalid phone number. This test is to check if the phone is being validated; not to check
     * if the actual validation works (that is handled by a framework).
     */
    @Test
    public void testInvalidPhone() {
        final String phoneNumber = "phoneNumber";
        ApiCompany company = random(ApiCompany.class, phoneNumber);
        company.setPhoneNumber("TESTTESTTESTTEST");
        Set<ConstraintViolation<ApiCompany>> v = validator.validate(company);
        assertEquals(1, v.size());
        // There is one constraint violation. This will access it using the iterator, and get the name of the field
        // that caused the violation. (see the documentation of ConstraintViolation)
        assertEquals(phoneNumber, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test an instance without any fields set.
     */
    @Test
    public void testNone() {
        ApiCompany company = new ApiCompany();
        assertEquals(4, validator.validate(company).size());
    }

    /**
     * Test an instance with some empty fields.
     */
    @Test
    public void testEmpty() {
        ApiCompany company = random(ApiCompany.class);
        company.setName("");
        company.setPhoneNumber("");
        company.setVatNumber("");
        assertEquals(3, validator.validate(company).size());
    }
}
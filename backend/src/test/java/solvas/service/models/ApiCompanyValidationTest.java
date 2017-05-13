package solvas.service.models;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.CompanyDao;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.utils.validators.UniqueVatNumberForCompanyValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test that {@link ApiCompany}'s validations work.
 *
 * @author Niko Strijbol
 */
public class ApiCompanyValidationTest extends ValidationTest {


    class CustomConstraintValidatorFactory implements ConstraintValidatorFactory{
        LocalValidatorFactoryBean factoryBean;
        public CustomConstraintValidatorFactory() {
            factoryBean = new LocalValidatorFactoryBean();
            factoryBean.setProviderClass(HibernateValidator.class);
            factoryBean.afterPropertiesSet();
        }

        @Override
        public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
            if (key.equals(UniqueVatNumberForCompanyValidator.class)){
                return (T) new UniqueVatNumberForCompanyValidator(context);
            }
            return factoryBean.getConstraintValidatorFactory().getInstance(key);
        }

        @Override
        public void releaseInstance(ConstraintValidator<?, ?> instance) {
            factoryBean.getConstraintValidatorFactory().releaseInstance(instance);
        }
    }





    /**
     * Setting up the tests
     */
    @Before
    @Override
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        factoryBean.setConstraintValidatorFactory(new CustomConstraintValidatorFactory());
        factoryBean.afterPropertiesSet();

        validator = factoryBean.getValidator();
    }

    @InjectMocks
    private UniqueVatNumberForCompanyValidator uniqueVatNumberForCompanyValidator;


    @Mock
    private DaoContext context;

    @Mock
    private CompanyDao companyDao;





    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiCompany company = random(ApiCompany.class);
        company.setPhoneNumber("+32 56 22 56 00");
        company.setVatNumber("random");
        emulateVatContraint(company.getVatNumber(),true);
        assertEquals(0, validator.validate(company).size());
    }

    /**
     * Test a company that has an invalid address.
     */
    @Test
    public void testInvalidAddress() {
        final String property = "address";
        ApiCompany company = random(ApiCompany.class);
        //company.setPhoneNumber("+32 56 22 56 22");
        company.setAddress(null);
        assertEquals(1, validator.validateProperty(company,property).size());

        ApiAddress emptyAddress = new ApiAddress();

        // Get how many errors the address produces
        int addressErrors = validator.validate(emptyAddress).size();

        company.setAddress(emptyAddress);
        assertEquals(0, validator.validateProperty(company,property).size()); // The address property is fine

        final String[] addressProperties = new String[] {"street","postalCode","houseNumber","country","city"};
        Arrays.stream(addressProperties).forEach((property2)-> assertEquals(1, validator.validateProperty(company.getAddress(),property2).size()));

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
        Set<ConstraintViolation<ApiCompany>> v = validator.validateProperty(company,"phoneNumber");
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
        emulateVatContraint(company.getVatNumber(),false);
        assertEquals(5, validator.validate(company).size());
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
        emulateVatContraint(company.getVatNumber(),false);
        assertEquals(3, validator.validate(company).size());
    }


    private void emulateVatContraint(String vat, boolean expected){
        when(context.getCompanyDao()).thenReturn(companyDao);
        Optional<Company> optional;
        if (expected) {
            optional=Optional.of(new Company());
        } else {
            optional=Optional.empty();

        }
        when(companyDao.findByVatNumber(vat)).thenReturn(Optional.empty());
    }
}
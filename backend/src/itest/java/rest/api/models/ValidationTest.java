package rest.api.models;

import org.junit.Before;

import javax.validation.Validator;

/**
 * Common code for testing the API model's validations.
 *
 * @author Niko Strijbol
 */
public class ValidationTest {

    protected Validator validator;

    /**
     * Initiate the validator.
     */
    @Before
    public void setUp() {
        validator = ValidatorConfiguration.createFactory().getValidator();
    }
}
package solvas.models.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.NotReadablePropertyException;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Niko Strijbol
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"squid:UndocumentedApi", "squid:S109"})
public class StartBeforeEndValidatorTest {

    @Mock
    private StartBeforeEnd annotation;

    @Mock
    private ConstraintValidatorContext context;

    private StartBeforeEndValidator validator;

    @Before
    public void setUp() throws Exception {
        when(annotation.endDate()).thenReturn("endDate");
        when(annotation.startDate()).thenReturn("startDate");

        ConstraintValidatorContext.ConstraintViolationBuilder builder =
                mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext customizableContext =
                mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);
        when(builder.addPropertyNode(anyString())).thenReturn(customizableContext);

        validator = new StartBeforeEndValidator();
        validator.initialize(annotation);
    }

    /**
     * Test for the validator, including logic tests.
     */
    @Test
    public void testIsValid() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusDays(50);
        LocalDateTime future = now.plusDays(50);

        Date nowNow = new Date(now, now);
        assertTrue(validator.isValid(nowNow, context));
        Date nowPast = new Date(now, past);
        assertFalse(validator.isValid(nowPast, context));
        Date nowFuture = new Date(now, future);
        assertTrue(validator.isValid(nowFuture, context));
        Date pastNow = new Date(past, now);
        assertTrue(validator.isValid(pastNow, context));
        Date pastFuture = new Date(past, future);
        assertTrue(validator.isValid(pastFuture, context));
        Date futurePast = new Date(future, past);
        assertFalse(validator.isValid(futurePast, context));
        Date nowOnly = new Date(now, null);
        assertTrue(validator.isValid(nowOnly, context));
    }

    @Test(expected = NotReadablePropertyException.class)
    public void testNoBeans() {
        validator.isValid(new Object(), context);
    }

    private class Date {
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        Date(LocalDateTime startDate, LocalDateTime endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public LocalDateTime getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
        }

        public LocalDateTime getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
        }
    }
}
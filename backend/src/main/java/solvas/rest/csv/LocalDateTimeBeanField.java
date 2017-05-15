package solvas.rest.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * @author Niko Strijbol
 */
public class LocalDateTimeBeanField extends AbstractBeanField<LocalDateTime> {

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, CsvConstraintViolationException {

        // We don't handle the constraint violations here, as constraints are checked by the validations on the model.
        // We do handle the empty value here.
        if (StringUtils.isEmpty(value)) {
            throw new CsvRequiredFieldEmptyException();
        }

        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            throw new CsvDataTypeMismatchException(value, LocalDateTime.class);
        }
    }
}

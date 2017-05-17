package solvas.rest.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Bean field for {@link LocalDateTime}.
 *
 * This converter will throw an exception if the field is not present, but will not check any constraints.
 *
 * @author Niko Strijbol
 */
public class LocalDateTimeBeanField extends AbstractBeanField<LocalDateTime> {

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, CsvConstraintViolationException {

        // Throw a more specific message if the field is empty. Otherwise it will also result in a
        // DateTimeParseException.
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
package solvas.rest.utils;

import com.opencsv.bean.CsvToBean;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Extends the csvToBean class from the OpenCsv library with custom mappings for types.
 * The standard CsvtoBean does not know mappings for any tpye other than the primitive types.
 *
 * The extra types for which mappings are defined:
 *  LocalDateTime
 *
 * @param <T> see CsvToBean
 *
 * Created by domien on 11/05/2017.
 */
public class MyCsvToBean<T> extends CsvToBean<T> {

    @Override
    protected Object convertValue(String value, PropertyDescriptor prop) throws InstantiationException,IllegalAccessException {

        if (prop.getPropertyType().getName().equals(LocalDateTime.class.getName()  )) {
            // return an custom object based on the incoming value
            return LocalDateTime.parse(value);
        }

        return super.convertValue(value, prop);
    }
}

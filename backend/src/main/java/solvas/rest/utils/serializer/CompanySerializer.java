package solvas.rest.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import solvas.models.Company;

import java.io.IOException;

/**
 * Created by steve on 11/03/2017.
 */
public class CompanySerializer extends StdSerializer<Company> {
    public CompanySerializer() {
        this(null);
    }

    public CompanySerializer(Class<Company> t) {
        super(t);
    }

    @Override
    public void serialize(Company value, JsonGenerator gen, SerializerProvider provider) throws IOException,JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumber(value.getId());
        gen.writeString(value.getName());
        gen.writeString(value.getPhoneNumber());
        //TODO adress
        //gen.writeString




        gen.writeEndObject();



    }
}

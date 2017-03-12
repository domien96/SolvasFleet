package solvas.rest.serializer;

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
    public void serialize(Company value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",value.getId());
        gen.writeStringField("name",value.getName());
        gen.writeStringField("vatNumber",value.getVatNumber());
        gen.writeStringField("phoneNumber",value.getPhoneNumber());

        //Address
        gen.writeFieldName("address");
        gen.writeStartObject();
        gen.writeStringField("country",value.getAddressCountry());
        gen.writeStringField("city",value.getAddressCountry());
        gen.writeStringField("street",value.getAddressStreet());
        gen.writeStringField("houseNumber",value.getAddressHouseNumber());
        gen.writeStringField("postalCode",value.getAddressPostalCode());
        gen.writeEndObject();


        gen.writeObjectField("createdAt",value.getCreatedAt());
        gen.writeObjectField("lastUpdated",value.getUpdatedAt());
        //TODO milestone 2
        gen.writeNumberField("lastUpdatedBy",0);
        gen.writeStringField("url",value.getUrl());

        gen.writeEndObject();



    }
}

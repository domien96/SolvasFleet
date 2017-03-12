package solvas.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import solvas.models.Role;

import java.io.IOException;

/**
 * Created by steve on 11/03/2017.
 */
public class RoleSerializer extends StdSerializer<Role> {
public RoleSerializer() {
        this(null);
        }

public RoleSerializer(Class<Role> t) {
        super(t);
        }


@Override
public void serialize(Role value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",value.getId());
        //gen.writeNumberField("company",value.get()); TODO add company
        gen.writeNumberField("company", 0);
        gen.writeStringField("function",value.getFunction());
        //gen.writeStringField("user",value.g()); TODO add role
        gen.writeNumberField("user",0);

        gen.writeObjectField("startDate", value.getStartDate());
        gen.writeObjectField("endDate", value.getEndDate());


        gen.writeObjectField("createdAt",value.getCreatedAt());
        gen.writeObjectField("lastUpdated",value.getUpdatedAt());
        //TODO milestone 2
        gen.writeNumberField("lastUpdatedBy",0);
        gen.writeStringField("url",value.getUrl());

        gen.writeEndObject();



        }
}

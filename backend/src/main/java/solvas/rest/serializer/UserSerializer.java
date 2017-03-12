package solvas.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import solvas.models.User;

import java.io.IOException;

/**
 * Created by steve on 11/03/2017.
 */
public class UserSerializer extends StdSerializer<User> {
    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }


    @Override
    public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",value.getId());
        gen.writeStringField("email", value.getEmail());



        gen.writeStringField("firstName\n",value.getFirstName());
        gen.writeStringField("lastName",value.getLastName());
        gen.writeStringField("password",value.getLastName());

        gen.writeObjectField("createdAt",value.getCreatedAt());
        gen.writeObjectField("lastUpdated",value.getUpdatedAt());
        //TODO milestone 2
        gen.writeNumberField("lastUpdatedBy",0);
        gen.writeStringField("url",value.getUrl());

        gen.writeEndObject();



    }
}
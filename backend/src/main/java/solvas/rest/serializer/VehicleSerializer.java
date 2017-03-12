package solvas.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import solvas.models.User;
import solvas.models.Vehicle;

import java.io.IOException;

/**
 * Created by steve on 11/03/2017.
 */
public class VehicleSerializer extends StdSerializer<Vehicle> {
    public VehicleSerializer() {
        this(null);
    }

    public VehicleSerializer(Class<Vehicle> t) {
        super(t);
    }


    @Override
    public void serialize(Vehicle value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",value.getId());
        gen.writeStringField("chassisNumber\n",value.getChassisNumber());
        gen.writeStringField("licensePlate",value.getLicensePlate());
        gen.writeNumberField("leasingCompany",value.getLeasingCompany().getId());
        gen.writeStringField("type",value.getType().getName());
        gen.writeStringField("model",value.getModel());
        gen.writeNumberField("year",value.getYear());
        gen.writeNumberField("mileage",value.getKilometerCount());
        //gen.writeNumberField("company",value.getCompany().getId());
        gen.writeStringField("brand",value.getBrand());







        gen.writeObjectField("createdAt",value.getCreatedAt());
        gen.writeObjectField("lastUpdated",value.getUpdatedAt());
        //TODO milestone 2
        gen.writeNumberField("lastUpdatedBy",0);
        gen.writeStringField("url",value.getUrl());

        gen.writeEndObject();



    }
}
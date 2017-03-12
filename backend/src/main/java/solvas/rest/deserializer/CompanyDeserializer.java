package solvas.rest.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import solvas.models.Company;
import solvas.models.Model;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by steve on 11/03/2017.
 */
public class CompanyDeserializer extends StdDeserializer<Company> {


    private ObjectMapper objectMapper;

    public CompanyDeserializer() {
        this(null);
    }


    private CompanyDeserializer(Class<?> vc) {
        super(vc);
    }
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Company deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        Company company = new Company();
        company.setId(node.has("id") ? (int) node.get("id").numberValue() : 0);
        company.setName(node.has("name") ? node.get("name").asText(): null);
        company.setVatNumber(node.has("vatNumber") ? node.get("vatNumber").asText(): null);
        company.setPhoneNumber(node.has("phoneNumber") ? node.get("phoneNumber").asText():null);
        JsonNode address =  node.path("address");
        company.setAddressCountry(address.has("country") ? address.get("country").asText():null);
        company.setAddressCity(address.has("city") ? address.get("city").asText():null);
        company.setAddressStreet(address.has("street") ? address.get("street").asText():null);
        company.setAddressHouseNumber(address.has("houseNumber") ? address.get("houseNumber").asText():null);
        company.setAddressPostalCode(address.has("postalCode") ? address.get("postalCode").asText():null);


        //Dates should not be set
        //company.setCreatedAt( objectMapper.treeToValue(node.get("createdAt"),LocalDateTime.class));


        return company;




    }
}

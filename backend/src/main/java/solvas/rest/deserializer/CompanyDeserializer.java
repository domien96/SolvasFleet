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
    public Company deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Company company = new Company();
        company.setId(node.has("id") ? (int) node.get("id").numberValue() : 0);
        company.setName(node.get("name").asText());
        company.setVatNumber(node.get("vatNumber").asText());
        company.setPhoneNumber(node.get("phoneNumber").asText());



        //company.setCreatedAt( objectMapper.treeToValue(node.get("createdAt"),LocalDateTime.class));

        //company.setCreatedAt( node.has("lastUpdated")?
              //  ctxt.readValue(node.get("lastUpdated").traverse(), LocalDateTime.class):null);
        return company;




    }
}

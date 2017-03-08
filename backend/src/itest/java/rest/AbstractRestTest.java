package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.List;


abstract class AbstractRestTest {

    ObjectMapper mapper=new ObjectMapper();

    String json(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }
}

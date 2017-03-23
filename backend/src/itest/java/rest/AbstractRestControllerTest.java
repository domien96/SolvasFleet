package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.rest.controller.AbstractRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

public abstract class AbstractRestControllerTest<T> {
     abstract AbstractRestController getController();
     private T apiModel;
     private String apiJson="";
     private Class<? extends T> clazz;
     public AbstractRestControllerTest(Class<?extends T> clazz)
     {
         this.clazz=clazz;
     }

     @Before
     public void config() throws JsonProcessingException {
         apiModel=random(clazz);
         ObjectMapper mapper=new ObjectMapper();
         mapper.findAndRegisterModules();
         apiJson=mapper.writeValueAsString(apiModel);
     }

    MockMvc getMockMvc()
    {
        return MockMvcBuilders.standaloneSetup(getController()).build();
    }

    public T getTestModel()
    {
        return apiModel;
    }

    public String getTestJson()
    {
        return apiJson;
    }
}

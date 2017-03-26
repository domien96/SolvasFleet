package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.rest.api.models.ApiModel;
import solvas.rest.controller.AbstractRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

/**
 * Abstract class of the restcontrollers
 * @param <T> The apimodel
 */
public abstract class AbstractRestControllerTest<T extends ApiModel> {
     abstract AbstractRestController getController();
     private T apiModel;
     private Class<? extends T> clazz;
      AbstractRestControllerTest(Class<?extends T> clazz)
     {
         this.clazz=clazz;
     }

    /**
     * Create a random model and its json representation
     */
     @Before
     public void config() throws JsonProcessingException {
         apiModel=random(clazz);
     }

    /**
     * Provides the Rest MVC mock
     * @return Rest MockMVC
     */
    MockMvc getMockMvc()
    {
        return MockMvcBuilders.standaloneSetup(getController()).build();
    }

    /**
     * Provides a random test model
     */
     T getTestModel()
    {
        return apiModel;
    }

    /**
     * Provides the json representation of the random test model
     */
     String getTestJson() throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(getTestModel());
    }
}

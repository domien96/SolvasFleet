package solvas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import solvas.rest.api.models.ApiModel;
import solvas.rest.controller.AbstractRestController;
import solvas.service.AbstractService;
import solvas.service.models.Model;

import java.util.List;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Abstract class of the restcontrollerstest, with some methods that are used in the subclasses
 * @param <E> The ApiModel
 * @param <T> The Model
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractBasicRestControllerTest<T extends Model, E extends ApiModel> {

        abstract AbstractRestController getController();

        private final static int LIST_SIZE = 100;

        private E apiModel;
        private List<? extends E> apiModelList;
        private Class<? extends E> clazz;

        @Mock
        private Validator v;

        private ObjectMapper objectMapper=new ObjectMapper().findAndRegisterModules();

        /**
         * Abstract test constructor
         * @param clazz the class we want to make random objects of.
         */
        public AbstractBasicRestControllerTest(Class<?extends E> clazz)
        {
            this.clazz=clazz;
        }

        /**
         * Create a random model and its json representation
         */
        @Before
        public void config() throws JsonProcessingException {
            apiModel = random(clazz);
            apiModelList = randomListOf(LIST_SIZE,clazz);
        }

        /**
         * Provides the Rest MVC mock
         * @return Rest MockMVC
         */
        MockMvc getMockMvc()
        {
            return MockMvcBuilders
                    .standaloneSetup(getController())
                    .setValidator(v)
                    .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                    .alwaysDo(MockMvcResultHandlers.print())
                    .build();
        }

        /**
         * Get the objectmapper (used for mapping to JSON)
         */
        ObjectMapper getObjectMapper()
        {
            return objectMapper;
        }

        /**
         * Provides a random test model
         */
        E getTestModel()
        {
            return apiModel;
        }

        /**
         * Provides a list of testmodels
         */
        List<? extends E> getTestModelList()
        {
            return apiModelList;
        }

        /**
         * Provides the json representation of the random test model
         */
        String getTestJson() throws JsonProcessingException {
            return getObjectMapper().writeValueAsString(getTestModel());
        }

        /**
         * Get the specific 'mocked' service
         */
        protected abstract AbstractService<T,E> getService();

        /**
         * Match jsonmodel with apimodel
         * @param res the resultaction that mockMvc provides.
         * @param model the model we want to compare with the json result
         */
        public void matchJsonModel(ResultActions res, E model) throws Exception {
            res.andExpect(content().json(getObjectMapper().writeValueAsString(model)));
        }
    }

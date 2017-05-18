package solvas.pdf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import solvas.Application;
import solvas.persistence.api.DaoContext;
import solvas.persistence.hibernate.HibernateConfig;
import solvas.rest.RestTestFixtures;
import solvas.rest.controller.VehicleRestController;
import solvas.rest.greencard.GreenCardViewResolver;
import solvas.rest.greencard.pdf.GreenCardPdfView;
import solvas.service.VehicleService;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan//("solvas")
@ContextConfiguration(classes = {
        Application.class,
        HibernateConfig.class
})
public class GreenCardTest {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DaoContext daoContext;

    private MockMvc getMockMvc() {
        ViewResolver greenCardViewResolver = new GreenCardViewResolver(new GreenCardPdfView(daoContext));
        return MockMvcBuilders
                .standaloneSetup(new VehicleRestController(vehicleService, null))
                .setViewResolvers(greenCardViewResolver)
                .build();
    }

    @Test
    public void greenCardDoesNotError() throws Exception {
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_ID_URL+"/greencard.pdf"))
                .andExpect(status().is2xxSuccessful());
    }
}

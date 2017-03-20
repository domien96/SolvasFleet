package rest;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import solvas.TestUtils;
import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.api.dao.RoleDao;
import solvas.persistence.api.dao.UserDao;
import solvas.rest.api.mappers.RoleAbstractMapper;
import solvas.rest.controller.RoleRestController;

import static org.mockito.Mockito.mock;

/**
 * Configuration that mocks the dao's and other stuff.
 *
 * @author Niko Strijbol
 */
public class MockConfig {

    @Bean
    RoleDao roleDao() {
        return mock(RoleDao.class);
    }
    @Bean
    CompanyDao companyDao() {
        return TestUtils.mockedCompanyDao();
    }
    @Bean
    UserDao userDao() {
        return TestUtils.mockedUserDao();
    }
    @Bean
    RoleAbstractMapper mapper() {
        return mock(RoleAbstractMapper.class);
    }
    @Bean
    RoleRestController controller(RoleDao dao, RoleAbstractMapper mapper) {
        return new RoleRestController(dao, mapper);
    }
    @Bean
    LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }
}

package solvas.persistence.api.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import solvas.Application;
import solvas.persistence.hibernate.HibernateConfig;

/**
 * General DAO test class. Useful primarily to prevent repeating all annotations on all other tests.
 *
 * @author Niko Strijbol
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class, HibernateConfig.class, TestConfig.class})
@DataJpaTest(excludeAutoConfiguration = {FlywayAutoConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public abstract class DaoTest {

    @Autowired
    TestEntityManager manager;

}
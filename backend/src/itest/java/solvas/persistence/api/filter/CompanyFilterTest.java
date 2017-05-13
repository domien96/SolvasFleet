package solvas.persistence.api.filter;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.api.dao.DaoTest;
import solvas.rest.query.CompanyFilter;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Company filter tests
 */
public class CompanyFilterTest extends DaoTest{

    @Autowired
    private CompanyDao companyDao;



    /**
     * Test when passing a empty filter
     */
    @Test
    public void withEmptyFilter() {

        CompanyFilter filter = new CompanyFilter();

        assertThat(companyDao.findAll(filter), hasSize(3));

    }


}

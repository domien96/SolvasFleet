package solvas;

import solvas.models.Company;
import solvas.models.User;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.api.dao.UserDao;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Utilities for tests.
 *
 * @author Niko Strijbol
 */
public class TestUtils {

    public static final int VALID_COMPANY = 500;
    public static final int VALID_USER = 1000;

    /**
     * Get a mock of a {@link CompanyDao} that contains one company that is findable
     * with {@link solvas.persistence.api.Dao#find(int)}. The ID is {@link #VALID_COMPANY}.
     *
     * @return The mock dao.
     */
    public static CompanyDao mockedCompanyDao() {
        Company company = random(Company.class);
        company.setId(VALID_COMPANY);
        CompanyDao dao = mock(CompanyDao.class);
        when(dao.find(anyInt())).then(invocation -> {
            if ((int) invocation.getArguments()[0] == VALID_COMPANY) {
                return company;
            } else {
                throw new EntityNotFoundException();
            }
        });
        return dao;
    }

    /**
     * Get a mock of a {@link UserDao} that contains one company that is findable
     * with {@link solvas.persistence.api.Dao#find(int)}. The ID is {@link #VALID_USER}.
     *
     * @return The mock dao.
     */
    public static UserDao mockedUserDao() {
        User user = random(User.class);
        user.setId(VALID_USER);
        UserDao dao = mock(UserDao.class);
        when(dao.find(anyInt())).then(invocation -> {
            if ((int) invocation.getArguments()[0] == VALID_USER) {
                return user;
            } else {
                throw new EntityNotFoundException();
            }
        });
        return dao;
    }
}

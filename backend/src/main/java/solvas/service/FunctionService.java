package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.Dao;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiFunction;
import solvas.service.mappers.AbstractMapper;
import solvas.service.models.Function;
import solvas.service.models.User;

import java.util.Collection;

@Service
public class FunctionService extends AbstractService<Function, ApiFunction> {
    /**
     * Contruct a function service
     *
     * @param modelDao the function dao
     * @param mapper   the mapper between the ApiFunction and the Function
     * @param userDao dao to find users
     */

    private final Dao<User> userDao;
    @Autowired
    public FunctionService(
            Dao<Function> modelDao,
            AbstractMapper<Function, ApiFunction> mapper,
            Dao<User> userDao) {
        super(modelDao, mapper);
        this.userDao = userDao;
    }

    public Collection<Function> getUserFunctions(User user) {
        return user.getFunctions();
    }

    public Collection<Function> getUserFunction(int userId) throws EntityNotFoundException {
        return getUserFunctions(userDao.find(userId));
    }
}

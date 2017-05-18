package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.Function;
import solvas.service.models.User;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.UserMapper;
import solvas.rest.api.models.ApiUser;

/**
 * Service class of user
 */
@Service
public class UserService extends AbstractService<User,ApiUser> {

    @Autowired
    private FunctionService functionService;

    /**
     * Construct a UserService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiUser and User
     */
    @Autowired
    public UserService(DaoContext context, UserMapper mapper) {
        super(context.getUserDao(),context, mapper);
    }


    @Override
    public void archive(int id) throws EntityNotFoundException {
        super.archive(id);
        User user = context.getUserDao().find(id);
        // Archive each function associated with this user
        for (Function function :  context.getFunctionDao().findByUserAndArchivedFalse(user)) {
            functionService.archive(function.getId());
        }
    }
}

package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.Function;
import solvas.service.models.User;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiUser;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.mappers.UserMapper;

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
    public void archive(int id) throws EntityNotFoundException, UnarchivableException {

        User user = context.getUserDao().find(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));

        // You cannot delete yourself.
        if (user.getId() == id) {
            throw new UnarchivableException();
        }

        // Archive each function associated with this user
        for (Function function :  context.getFunctionDao().findByUserAndArchivedFalse(user)) {
            functionService.archive(function.getId());
        }

        super.archive(id);
    }
}

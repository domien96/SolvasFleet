package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiUser;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.mappers.UserMapper;
import solvas.service.models.User;

/**
 * Service class of user
 */
@Service
public class UserService extends AbstractService<User,ApiUser> {

    private DaoContext context;

    /**
     * Construct a UserService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiUser and User
     */
    @Autowired
    public UserService(DaoContext context, UserMapper mapper) {
        super(context.getUserDao(), mapper);
        this.context = context;
    }

    @Override
    public void archive(int id) throws EntityNotFoundException, UnarchivableException {

        User user = context.getUserDao().findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        // You cannot delete yourself.
        if (user.getId() == id) {
            throw new UnarchivableException();
        }

        super.archive(id);
    }
}

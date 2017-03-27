package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.User;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.UserDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.UserMapper;
import solvas.rest.api.models.ApiUser;

/**
 * Service class of user
 */
@Service
public class UserService extends AbstractService<User,ApiUser> {

    /**
     * Construct a UserService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiUser and User
     */
    @Autowired
    public UserService(DaoContext context, UserMapper mapper) {
        super(context.getUserDao(), mapper);
    }
}

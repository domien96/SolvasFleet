package solvas.rest.service;

import solvas.models.User;
import solvas.persistence.api.Dao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.models.ApiUser;

/**
 * Service class of user
 */
public class UserService extends AbstractService<User,ApiUser> {
    public UserService(Dao<User> modelDao, AbstractMapper<User, ApiUser> mapper) {
        super(modelDao, mapper);
    }
}

package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.service.models.User;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.SimpleUrlBuilder;
import solvas.rest.api.models.ApiUser;

/**
 * Mapper between User and ApiUser
 */
@Component
public class UserMapper extends AbstractMapper<User,ApiUser> {

    private static final String ROOTPATH ="/users/";
    /**
     * Create UserMapper
     *
     * @param daoContext The DAO context
     */
    public UserMapper(DaoContext daoContext) {
        super(daoContext, "firstName", "lastName", "email", "password");
    }

    @Override
    public User convertToModel(ApiUser apiUser) throws FieldNotFoundException ,EntityNotFoundException {
        User user = apiUser.getId()==0 ? new User() : daoContext.getUserDao().find(apiUser.getId());
        copySharedAttributes(user, apiUser);
        return user;
    }

    @Override
    public ApiUser convertToApiModel(User user) throws FieldNotFoundException {
        ApiUser apiUser = new ApiUser();
        copyAttributes(apiUser,user, "id","createdAt", "updatedAt");
        copySharedAttributes(apiUser, user);
        apiUser.setUrl(SimpleUrlBuilder.buildUrl(ROOTPATH + "{id}", user.getId()));
        return apiUser;
    }
}

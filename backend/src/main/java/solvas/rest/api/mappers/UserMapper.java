package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.User;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiUser;

/**
 * Mapper between User and ApiUser
 */
@Component
public class UserMapper extends AbstractMapper<User,ApiUser> {

    private String rootPath="/users/";
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
        apiUser.setUrl(rootPath+apiUser.getId());
        return apiUser;
    }
}

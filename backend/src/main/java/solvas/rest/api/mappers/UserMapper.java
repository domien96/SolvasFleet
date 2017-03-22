package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.User;
import solvas.persistence.api.DaoContext;
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
        super(daoContext);
    }

    @Override
    public User convertToModel(ApiUser apiUser) {
        User user = apiUser.getId()==0?new User():daoContext.getUserDao().find(apiUser.getId());
        user.setFirstName(apiUser.getFirstName());
        user.setLastName(apiUser.getLastName());
        user.setEmail(apiUser.getEmail());
        if(apiUser.getPassword() != null && apiUser.getPassword().length() > 0) {
            user.setPassword(apiUser.getPassword());
        }
        return user;
    }

    @Override
    public ApiUser convertToApiModel(User user) {
        ApiUser apiUser = new ApiUser();
        apiUser.setId(user.getId());
        apiUser.setFirstName(user.getFirstName());
        apiUser.setLastName(user.getLastName());
        apiUser.setEmail(user.getEmail());
        apiUser.setPassword(user.getPassword());
        apiUser.setUpdatedAt(user.getUpdatedAt());
        apiUser.setCreatedAt(user.getCreatedAt());
        apiUser.setUrl(rootPath+apiUser.getId());
        return apiUser;
    }
}

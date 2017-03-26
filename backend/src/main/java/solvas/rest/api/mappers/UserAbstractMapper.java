package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import solvas.models.User;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.SimpleUrlBuilder;
import solvas.rest.api.models.ApiUser;

/**
 * Mapper between User and ApiUser
 */
@Component
public class UserAbstractMapper extends AbstractMapper<User,ApiUser> {

    private static final String ROOTPATH ="/users/";
    /**
     * Create UserMapper
     *
     * @param daoContext The DAO context
     */
    public UserAbstractMapper(DaoContext daoContext) {
        super(daoContext, "firstName", "lastName", "email", "password");
    }

    @Override
    public User convertToModel(ApiUser apiUser) throws FieldNotFoundException ,EntityNotFoundException {

        User user = new User();
        user.setId(apiUser.getId());
        if (user.getId()!=0) {
            //update
            user = daoContext.getUserDao().find(user.getId());
        }
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

package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import solvas.models.User;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiUser;

/**
 * Mapper between User and ApiUser
 */
@Component
public class UserAbstractMapper extends AbstractMapper<User,ApiUser> {

    private String ROOTPATH ="/users/";
    /**
     * Create UserMapper
     *
     * @param daoContext The DAO context
     */
    public UserAbstractMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public User convertToModel(ApiUser apiUser) throws EntityNotFoundException {
        User user = new User();
        user.setId(apiUser.getId());
        if (user.getId()!=0) {
            //update
            user = daoContext.getUserDao().find(user.getId());
        }

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
        UriComponentsBuilder bldr = ServletUriComponentsBuilder.fromCurrentRequest();
        apiUser.setUrl(bldr.path(ROOTPATH +"{id}").buildAndExpand(user.getId()).toUriString());
        return apiUser;
    }
}

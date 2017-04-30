package solvas.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import solvas.service.models.User;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.utils.SimpleUrlBuilder;
import solvas.rest.api.models.ApiUser;

/**
 * Mapper between User and ApiUser
 */
@Component
public class UserMapper extends AbstractMapper<User,ApiUser> {

    private static final String ROOTPATH ="/users/";
    private final PasswordEncoder passwordEncoder;
    /**
     * Create UserMapper
     *
     * @param daoContext The DAO context
     * @param passwordEncoder Encoder for passwords to avoid storing plaintext
     */
    @Autowired
    public UserMapper(DaoContext daoContext, PasswordEncoder passwordEncoder) {
        super(daoContext, "firstName", "lastName", "email");
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convertToModel(ApiUser apiUser) throws FieldNotFoundException ,EntityNotFoundException {
        User user = apiUser.getId()==0 ? new User() : daoContext.getUserDao().find(apiUser.getId());
        copySharedAttributes(user, apiUser);
        if(apiUser.getPassword() != null && ! apiUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(apiUser.getPassword()));
        }
        return user;
    }

    @Override
    public ApiUser convertToApiModel(User user) throws FieldNotFoundException {
        ApiUser apiUser = new ApiUser();
        copyAttributes(apiUser,user, "id","createdAt", "updatedAt");
        copySharedAttributes(apiUser, user);
        apiUser.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH + "{id}", user.getId()));
        return apiUser;
    }
}

package solvas.rest.api.mappings;

import solvas.models.User;
import solvas.rest.api.models.ApiUser;

/**
 * Created by steve on 11/03/2017.
 */
public class UserMapping extends Mapping<User,ApiUser> {

    @Override
    public User convertToModel(ApiUser apiUser) {
        User user = new User();
        user.setId(apiUser.getId());
        user.setFirstName(apiUser.getFirstName());
        user.setLastName(apiUser.getLastName());
        user.setEmail(apiUser.getEmail());
        user.setPassword(apiUser.getPassword());
        user.setUpdatedAt(apiUser.getUpdatedAt());
        user.setCreatedAt(apiUser.getCreatedAt());
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
        return apiUser;
    }
}

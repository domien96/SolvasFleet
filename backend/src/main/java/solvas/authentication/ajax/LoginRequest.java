package solvas.authentication.ajax;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model intended to be used for AJAX based authentication.
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Let jackson read JSON into a LoginRequest instance
     * @param username Username of this user
     * @param password Password of this user
     */
    @JsonCreator
    public LoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return Username of the user that wishes to authenticate
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The password provided by the user
     */
    public String getPassword() {
        return password;
    }
}

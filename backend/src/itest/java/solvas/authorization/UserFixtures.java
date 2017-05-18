package solvas.authorization;

import solvas.authentication.ajax.LoginRequest;

/**
 * Login credentials for some users
 */
public class UserFixtures {
    /**
     * Admin user with all permissions
     */
    public final static LoginRequest ADMINISTRATOR = new LoginRequest("metus.vitae.velit@facilisismagnatellus.net", "WBL90DHP2RU");
    /**
     * User without permissions
     */
    public final static LoginRequest NO_PERMISSIONS = new LoginRequest("eleifend.Cras@tellusimperdiet.edu","GTZ20SSS5HV");
}

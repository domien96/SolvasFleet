package solvas.authentication.jwt.token;

/**
 * Scopes
 */
public enum Scopes {
    REFRESH_TOKEN;

    /**
     * @return String representation of the authority of this Scope
     */
    public String authority() {
        return "ROLE_" + this.name();
    }
}
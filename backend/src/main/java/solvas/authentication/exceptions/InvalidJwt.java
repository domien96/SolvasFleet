package solvas.authentication.exceptions;

import solvas.authentication.jwt.token.RawAccessJwtToken;

/**
 * This exception is thrown when a user attempts to pass an invalid (for example: self-signed) JWT
 */
public class InvalidJwt extends Exception {
    /**
     * @param token The invalid CAS token
     */
    public InvalidJwt(RawAccessJwtToken token) {
        super(token.getToken());
    }
}

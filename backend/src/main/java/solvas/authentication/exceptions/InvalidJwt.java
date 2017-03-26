package solvas.authentication.exceptions;

import solvas.authentication.jwt.token.RawAccessJwtToken;

public class InvalidJwt extends Exception {
    public InvalidJwt(RawAccessJwtToken token) {
        super(token.getToken());
    }
}

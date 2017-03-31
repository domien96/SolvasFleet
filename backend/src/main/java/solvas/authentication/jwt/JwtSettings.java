package solvas.authentication.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import solvas.authentication.jwt.token.JwtToken;

/**
 * Configuration for the JWT authentication service
 */
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@PropertySource(value = {"security.properties"})
public class JwtSettings {
    private Integer tokenExpirationTime;

    private String tokenIssuer;

    private String tokenSigningKey;
    

    private Integer refreshTokenExpTime;

    /**
     * @return get duration before refresh tokens expire
     */
    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    /**
     * set duration before refresh tokens expire
     * @param refreshTokenExpTime new duration before refresh tokens expire
     */
    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    /**
     * @return get duration before access tokens expire
     */
    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    /**
     * set duration before access token expire
     * @param tokenExpirationTime new duration before access tokens expire
     */
    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    /**
     * @return get the name of the token issuer
     */
    public String getTokenIssuer() {
        return tokenIssuer;
    }

    /**
     * set the name of the token issuer
     * @param tokenIssuer name of the token issuer
     */
    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }


    /**
     * @return get the secret signing key
     */
    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    /**
     * @param tokenSigningKey set the secret signing key
     */
    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }
}
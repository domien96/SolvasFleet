package solvas.authentication.jwt;

/**
 * Generify extracting tokens
 */
public interface TokenExtractor {

    /**
     * Extract JWT from string
     * @param header string to extract token from
     * @return String representation of extracted JWT
     */
    String extract(String header);
}

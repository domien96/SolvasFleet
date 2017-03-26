package solvas.authentication.jwt;

/**
 * Created by David Vandorpe.
 */
public interface TokenExtractor {

    String extract(String header);
}

package solvas.authentication;

/**
 * Created by David Vandorpe.
 */
public interface TokenExtractor {

    String extract(String header);
}

package solvas.rest;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Helps in dynamically building Url's.
 * Created by domien on 25/03/2017.
 */
public class SimpleUrlBuilder extends UriComponentsBuilder{

    /**
     * Makes up the full url with the given path and parameters
     * @param path the path with parameters surrounded by curly braces.
     *             e.g. /users/{id}
     * @param parameters The values for the parameters mentioned in the path in order.
     * @return The builded url
     */
    public static String buildUrl(String path, Object... parameters ) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replacePath(path);
        return builder.buildAndExpand(parameters).toUriString();
    }
}

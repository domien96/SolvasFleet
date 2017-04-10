package solvas.rest.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Helps in dynamically building Url's.
 * Created by domien on 25/03/2017.
 */
public class SimpleUrlBuilder extends UriComponentsBuilder {

    /**
     * Makes up the full url by appending the given path and parameters
     * after the base of a full url which is defined as the following parts:
     * protocol://authentication@domain:port/
     * @param path the path with parameters surrounded by curly braces.
     *             e.g. /users/{id}
     * @param parameters The values for the parameters mentioned in the path in order.
     * @return The builded url which has following format protocol://authentication@domain:port/path
     * with the parameters filled in.
     */
    public static String buildUrlFromBase(String path, Object... parameters ) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQuery(null);
        builder.replacePath(path);
        return builder.buildAndExpand(parameters).toUriString();
    }
}

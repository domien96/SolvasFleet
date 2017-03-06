package solvas.rest.utils;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by David Vandorpe.
 */
public class JsonListWrapper<T> extends HashMap<String, Collection<T>> {
    public JsonListWrapper() {}

    public JsonListWrapper(Collection<T> items) {
        this(items, "items");
    }

    public JsonListWrapper(Collection<T> items, String key) {
        put(key, items);
    }
}

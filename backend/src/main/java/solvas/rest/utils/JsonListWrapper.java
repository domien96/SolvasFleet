package solvas.rest.utils;

import java.util.Collection;
import java.util.HashMap;

/**
 * Class to wrap JSON arrays in a JSON object.
 *
 * This class extends a HashMap with generics {@code <String, Object>}. This allows you to add additional items
 * to the object. An example of the intended use is adding pagination information.
 *
 * @param <T> Type of items that should be wrapped
 */
public class JsonListWrapper<T> extends HashMap<String, Object> {
    /**
     * Default no-arg constructor. You can set keys as you would for a map
     */
    public JsonListWrapper() {}

    /**
     *  Use the default key ("items")
     *
     *  @param items the collection that should be wrapped
     */
    public JsonListWrapper(Collection<T> items) {
        this(items, "data");
    }

    /**
     * Create a JSON object that has key mapped to items
     *
     * @param items the collection that should be wrapped
     * @param key name of the key that should contain the items
     */
    public JsonListWrapper(Collection<T> items, String key) {
        put(key, items);
    }
}
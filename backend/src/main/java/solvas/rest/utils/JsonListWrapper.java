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

    public static final String ERROR_KEY = "errors";
    public static final String DATA_KEY = "data";
    public static final String PAGE_TOTAL_KEY = "total";

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
        this(items, DATA_KEY);
    }

    /**
     * Get a representation of the data with a total. The resulting wrapper contains a field {@code data} with the
     * elements and a field {@code total} containing the number of elements.
     *
     * @param items The items to add.
     * @param <E>   The type of items.
     *
     * @return The representation. You must not edit the amount of elements in the wrapper.
     */
    public static <E> JsonListWrapper<E> withTotal(Collection<E> items) {
        JsonListWrapper<E> listWrapper = new JsonListWrapper<>(items);
        listWrapper.put(PAGE_TOTAL_KEY, items.size());
        return listWrapper;
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
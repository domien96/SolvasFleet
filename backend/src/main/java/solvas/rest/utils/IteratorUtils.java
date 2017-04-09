package solvas.rest.utils;

import java.util.ArrayList;
import java.util.List;

public class IteratorUtils {

    /**
     * Collect an iterable to a list.
     *
     * @param iterable The iterable.
     *
     * @param <T> Type.
     *
     * @return The list.
     */
    public static <T> List<T> toList(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<T>();
        iterable.iterator().forEachRemaining(list::add);
        return list;
    }
}

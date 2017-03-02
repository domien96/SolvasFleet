package solvas.persistence;

import org.hibernate.Session;

import java.util.function.Consumer;

@FunctionalInterface
public interface Query<R> {

    R run(Session s);

    /**
     * Modify the query to discard the result.
     *
     * @return The query, but without result.
     */
    static Query<Void> empty(Consumer<Session> query) {
        return s -> {
            query.accept(s);
            return null;
        };
    }
}
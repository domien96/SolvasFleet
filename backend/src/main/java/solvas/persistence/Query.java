package solvas.persistence;

import org.hibernate.Session;

import java.util.function.Consumer;

/**
 * A query to get data.
 *
 * @param <R> The return value of the query.
 */
@FunctionalInterface
public interface Query<R> {

    /**
     * Execute the query to produce a result.
     *
     * @param s The session on which the query must be run.
     *
     * @return The result of the query.
     */
    R run(Session s);

    /**
     * Modify the query to discard the result. It actually converts a {@link Consumer} to a {@link Query},
     * but conceptually a Consumer can be thought of as a Query without return value.
     *
     * @param query The query to execute.
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
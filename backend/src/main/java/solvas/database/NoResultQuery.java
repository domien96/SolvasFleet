package solvas.database;

import org.hibernate.Session;

/**
 * Created by david on 3/1/17.
 */
public class NoResultQuery implements Query<Void> {
    private final Query query;
    public NoResultQuery(Query query) {
        this.query = query;
    }
    @Override
    public Void run(Session s) {
        query.run(s);
        return null;
    }

    public interface Query {
        void run(Session s);
    }
}

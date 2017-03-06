package solvas.database;

import org.hibernate.Session;

public interface Query<Return> {
    Return run(Session s);
}

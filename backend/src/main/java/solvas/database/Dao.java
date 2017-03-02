package solvas.database;

import org.springframework.core.GenericTypeResolver;
import solvas.models.Company;
import solvas.models.Model;

public class Dao<T> {
    private final Class<T> type;

    public Dao(Class<T> type) {
        this.type = type;
    }

    public T save(T model) {
        createConnection().run(
                new NoResultQuery(s -> s.saveOrUpdate(model))
        );

        return model;
    }


    public T destroy(T model) {
        createConnection().run(
                new NoResultQuery(s -> s.delete(model))
        );

        return model;
    }

    public T find(int id) {
        return createConnection().run(
                s -> (T) s.get(type, id)
        );
    }

    private DatabaseConnection createConnection() {
        return new DatabaseConnection();
    }
}
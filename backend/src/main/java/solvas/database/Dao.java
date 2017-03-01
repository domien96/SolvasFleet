package solvas.database;

import org.springframework.core.GenericTypeResolver;
import solvas.models.Company;
import solvas.models.Model;

public class Dao<T extends Model> {

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
        // Casts for everyone!
        return createConnection().run(
                s -> (T) s.get(GenericTypeResolver.resolveTypeArgument(getClass(), Dao.class), id)
        );
    }

    private DatabaseConnection createConnection() {
        return new DatabaseConnection();
    }
}
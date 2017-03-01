package solvas.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import solvas.models.Company;

/**
 * Created by david on 3/1/17.
 */
public class DatabaseConnection implements AutoCloseable {
    private static SessionFactory factory;

    static {
        Configuration config = new Configuration().configure();
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

        factory = new Configuration().configure().buildSessionFactory(registry);
    }

    public <Return> Return run(Query<Return> query) {
        Session session = factory.openSession();

        Return result = null;
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            //result = query.run(session);
            result = (Return) session.get(Company.class, 2);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}

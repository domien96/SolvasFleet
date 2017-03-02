package solvas.models;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ManageCompany {
    private SessionFactory factory;

    public ManageCompany() {

        Configuration config = new Configuration().configure();

        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

        factory = new Configuration().configure().buildSessionFactory(registry);
    }

    /* Method to CREATE an employee in the database */
    public Integer addCompany(String lname, String vat) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer companyId = null;
        try {
            tx = session.beginTransaction();
            Company c = new Company(lname, vat);
            companyId = (Integer) session.save(c);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return companyId;
    }
}
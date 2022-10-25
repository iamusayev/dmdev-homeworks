import entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {


    private static final Customer CUSTOMER = Customer.builder()
            .surname("test")
            .password("test1337")
            .firstName("test")
            .email("test@gmail.com")
            .build();

    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        Configuration configure = configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.save(CUSTOMER);
            }
        }
    }

}

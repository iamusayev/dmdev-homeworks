package util;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    public static Session buildSession() {
        Configuration configuration = new Configuration();
        Configuration configure = configuration.configure();
        return configuration.buildSessionFactory().openSession();
    }
}

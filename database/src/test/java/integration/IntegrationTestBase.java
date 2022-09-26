package integration;

import util.TestObjectUtils;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import util.HibernateUtil;

public abstract class IntegrationTestBase {

    private static final String CREATE_CUSTOMERS_TABLE = """
            
            CREATE TABLE customers (
                id SERIAL PRIMARY KEY,
                first_name VARCHAR(128) NOT NULL ,
                surname VARCHAR(128) NOT NULL ,
                email VARCHAR (128) UNIQUE NOT NULL,
                password VARCHAR(128) NOT NULL
                );
            
            """;

    private static final String CREATE_INFO_TABLE = """
            
            CREATE TABLE info (
                id SERIAL PRIMARY KEY ,
                phone_number VARCHAR(32) NOT NULL ,
                address VARCHAR(256) NOT NULL,
                customer_id INT REFERENCES customers (id)
            );
            
            """;

    private static final String CREATE_PRODUCTS_TABLE = """
                    
            CREATE TABLE products(
                id SERIAL PRIMARY KEY ,
                name VARCHAR(256) NOT NULL ,
                description TEXT NOT NULL ,
                price NUMERIC (16,2) NOT NULL,
                remaining_quantity INT NOT NULL
                );
                    
            """;
    private static final String CREATE_ORDERS_TABLE = """
                    
            CREATE TABLE orders (
                id SERIAL PRIMARY KEY ,
                registration_date DATE,
                closing_date DATE NOT NULL,
                status VARCHAR(32) NOT NULL,
                customer_id INT REFERENCES customers(id)
                );
                       
            """;

    private static final String CREATE_PRODUCT_ORDER_TABLE = """
                        
            CREATE TABLE product_order(
                product_id INT REFERENCES  products(id),
                order_id INT REFERENCES orders(id)
                );         
            
            """;

    private static final String DROP_TABLE = "DROP  TABLE IF EXISTS %s CASCADE";

    public static final Integer EXISTING_ID = 1;
    public static final Integer NON_EXISTENT_ID = Integer.MAX_VALUE;

    @BeforeEach
    @SneakyThrows
    void  prepareDatabase() {
        try (Session session = HibernateUtil.buildSession()) {
            session.beginTransaction();

            session.createSQLQuery(DROP_TABLE.formatted("orders")).executeUpdate();
            session.createSQLQuery(DROP_TABLE.formatted("customers")).executeUpdate();
            session.createSQLQuery(DROP_TABLE.formatted("product_order")).executeUpdate();
            session.createSQLQuery(DROP_TABLE.formatted("products")).executeUpdate();
            session.createSQLQuery(DROP_TABLE.formatted("info")).executeUpdate();

            session.createSQLQuery(CREATE_CUSTOMERS_TABLE).executeUpdate();
            session.createSQLQuery(CREATE_INFO_TABLE).executeUpdate();
            session.createSQLQuery(CREATE_PRODUCTS_TABLE).executeUpdate();
            session.createSQLQuery(CREATE_ORDERS_TABLE).executeUpdate();
            session.createSQLQuery(CREATE_PRODUCT_ORDER_TABLE).executeUpdate();

            session.save(TestObjectUtils.CUSTOMER);
            session.save(TestObjectUtils.INFO);
            session.save(TestObjectUtils.ORDER);
            session.save(TestObjectUtils.PRODUCT);

            session.getTransaction().commit();
        }
    }
}

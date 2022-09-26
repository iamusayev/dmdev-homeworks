package integration.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import entity.Customer;
import integration.IntegrationTestBase;
import util.TestObjectUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

public class CustomerIT extends IntegrationTestBase {

    private static final Customer CUSTOMER = Customer.builder()
            .email("test123@gmail.com")
            .password("qwerty")
            .firstName("test")
            .surname("test")
            .build();

    private final Session session = HibernateUtil.buildSession();

    @Test
    void shouldSaveCorrectEntity() {
        var expectedCustomer = CUSTOMER;

        session.save(expectedCustomer);
        Customer actualCustomer = session.get(Customer.class, 2);

        assertThat(actualCustomer).isNotNull();
        assertThat(expectedCustomer.getEmail()).isEqualTo(actualCustomer.getEmail());
    }

    @Test
    void shouldFindExistingEntity() {
        Customer customer = session.get(Customer.class, IntegrationTestBase.EXISTING_ID);
        assertThat(customer).isNotNull();
    }

    @Test
    void shouldReturnEmptyIfEntityDoesNotExist() {
        Customer customer = session.get(Customer.class, IntegrationTestBase.NON_EXISTENT_ID);
        assertThat(customer).isNull();
    }

    @Test
    void shouldUpdateExistingEntity() {
        var expectedCustomer = CUSTOMER;
        expectedCustomer.setEmail("123@gmail.com");
        expectedCustomer.setFirstName("hey1337");
        expectedCustomer.setPassword("hey1337");
        expectedCustomer.setSurname("test1337");

        session.saveOrUpdate(expectedCustomer);
        Customer actualCustomer = session.get(Customer.class, IntegrationTestBase.EXISTING_ID);

        assertAll(
                () -> assertThat(expectedCustomer).isNotEqualTo(actualCustomer),
                () -> assertThat(expectedCustomer.getEmail()).isNotEqualTo(actualCustomer.getEmail()),
                () -> assertThat(expectedCustomer.getFirstName()).isNotEqualTo(actualCustomer.getFirstName()),
                () -> assertThat(expectedCustomer.getPassword()).isNotEqualTo(actualCustomer.getPassword()),
                () -> assertThat(expectedCustomer.getSurname()).isNotEqualTo(actualCustomer.getSurname())
        );
    }

    @Test
    void shouldDeleteExistingEntity() {
        session.delete(TestObjectUtils.CUSTOMER);

        Customer customer = session.get(Customer.class, IntegrationTestBase.EXISTING_ID);

        assertThat(customer).isNull();
    }
}

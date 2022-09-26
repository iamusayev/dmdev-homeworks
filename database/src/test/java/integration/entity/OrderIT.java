package integration.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import entity.Order;
import integration.IntegrationTestBase;
import util.TestObjectUtils;
import java.time.LocalDate;
import model.Status;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

public class OrderIT extends IntegrationTestBase {

    private static final Order ORDER = Order.builder()
            .registrationDate(LocalDate.of(2020, 2, 20))
            .closingDate(LocalDate.of(2020, 2, 22))
            .status(Status.ACTIVE)
            .build();

    private final Session session = HibernateUtil.buildSession();

    @Test
    void shouldSaveCorrectEntity() {
        var expectedOrder = ORDER;

        session.save(expectedOrder);
        Order actualOrder = session.get(Order.class, 2);

        assertThat(actualOrder).isNotNull();
    }

    @Test
    void shouldFindExistingEntity() {
        Order order = session.get(Order.class, IntegrationTestBase.EXISTING_ID);
        assertThat(order).isNotNull();
    }

    @Test
    void shouldReturnEmptyIfEntityDoesNotExist() {
        Order order = session.get(Order.class, IntegrationTestBase.NON_EXISTENT_ID);
        assertThat(order).isNull();
    }

    @Test
    void shouldUpdateExistingEntity() {
        var expectedOrder = ORDER;
        expectedOrder.setRegistrationDate(LocalDate.of(2022,2,2));
        expectedOrder.setClosingDate(LocalDate.of(2022,1,1));
        expectedOrder.setStatus(Status.INACTIVE);

        session.saveOrUpdate(expectedOrder);
        Order actualOrder = session.get(Order.class, IntegrationTestBase.EXISTING_ID);

        assertAll(
                () -> assertThat(expectedOrder).isNotEqualTo(actualOrder),
                () -> assertThat(expectedOrder.getRegistrationDate()).isNotEqualTo(actualOrder.getRegistrationDate()),
                () -> assertThat(expectedOrder.getClosingDate()).isNotEqualTo(actualOrder.getClosingDate()),
                () -> assertThat(expectedOrder.getStatus()).isNotEqualTo(actualOrder.getStatus())
        );
    }

    @Test
    void shouldDeleteExistingEntity() {
        session.delete(TestObjectUtils.ORDER);

        Order order = session.get(Order.class, IntegrationTestBase.EXISTING_ID);

        assertThat(order).isNull();
    }
}

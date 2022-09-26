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
        Order actualOrder;

        try(session){
        session.beginTransaction();
        session.save(expectedOrder);
        actualOrder = session.get(Order.class, TestObjectUtils.JUST_CREATED_ID);
        session.getTransaction().commit();
        }

        assertThat(actualOrder).isNotNull();
    }

    @Test
    void shouldFindExistingEntity() {
        Order order;
        
        try(session){
        session.beginTransaction();
        order = session.get(Order.class, TestObjectUtils.EXISTING_ID);
        session.getTransaction().commit();
        }
        
        assertThat(order).isNotNull();
    }

    @Test
    void shouldReturnEmptyIfEntityDoesNotExist() {
        Order order;
        
        try(session){
        session.beginTransaction();
        order = session.get(Order.class, TestObjectUtils.NON_EXISTENT_ID);
        session.getTransaction().commit();
        }
    
        assertThat(order).isNull();
    }

    @Test
    void shouldUpdateExistingEntity() {
        var expectedOrder = ORDER;
        expectedOrder.setRegistrationDate(LocalDate.of(2022,2,2));
        expectedOrder.setClosingDate(LocalDate.of(2022,1,1));
        expectedOrder.setStatus(Status.INACTIVE);
        Order actualOrder;
        
        try(session){
        session.beginTransaction();
        session.saveOrUpdate(expectedOrder);
        actualOrder = session.get(Order.class, TestObjectUtils.EXISTING_ID);
        session.getTransaction().commit();
        }

        assertAll(
                () -> assertThat(expectedOrder).isNotEqualTo(actualOrder),
                () -> assertThat(expectedOrder.getRegistrationDate()).isNotEqualTo(actualOrder.getRegistrationDate()),
                () -> assertThat(expectedOrder.getClosingDate()).isNotEqualTo(actualOrder.getClosingDate()),
                () -> assertThat(expectedOrder.getStatus()).isNotEqualTo(actualOrder.getStatus())
        );
    }

    @Test
    void shouldDeleteExistingEntity() {
        Order oder;
        
        try(session){
        session.delete(TestObjectUtils.ORDER);
        order = session.get(Order.class, TestObjectUtils.EXISTING_ID); 
        }

        assertThat(order).isNull();
    }
}

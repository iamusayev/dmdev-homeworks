package integration.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import entity.Product;
import integration.IntegrationTestBase;
import util.TestObjectUtils;
import java.math.BigDecimal;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

public class ProductIT extends IntegrationTestBase {

    private static final Product PRODUCT = Product.builder()
            .name("test123456")
            .description("some description")
            .price(BigDecimal.valueOf(99, 99))
            .remainingQuantity(10)
            .build();

    private final Session session = HibernateUtil.buildSession();

    @Test
    void shouldSaveCorrectEntity() {
        var expectedProduct = PRODUCT;

        session.save(expectedProduct);
        Product actualProduct = session.get(Product.class, 2);

        assertThat(actualProduct).isNotNull();
    }

    @Test
    void shouldFindExistingEntity() {
        Product product = session.get(Product.class, IntegrationTestBase.EXISTING_ID);
        assertThat(product).isNotNull();
    }

    @Test
    void shouldReturnEmptyIfEntityDoesNotExist() {
        Product product = session.get(Product.class, IntegrationTestBase.NON_EXISTENT_ID);
        assertThat(product).isNull();
    }

    @Test
    void shouldUpdateExistingEntity() {
        var expectedProduct = PRODUCT;
        expectedProduct.setName("anotherName");
        expectedProduct.setDescription("anotherDescription");
        expectedProduct.setPrice(BigDecimal.valueOf(100));
        expectedProduct.setRemainingQuantity(99);

        session.saveOrUpdate(expectedProduct);
        Product actualOrder = session.get(Product.class, IntegrationTestBase.EXISTING_ID);

        assertAll(
                () -> assertThat(expectedProduct).isNotEqualTo(actualOrder),
                () -> assertThat(expectedProduct.getName()).isNotEqualTo(actualOrder.getName()),
                () -> assertThat(expectedProduct.getDescription()).isNotEqualTo(actualOrder.getDescription()),
                () -> assertThat(expectedProduct.getPrice()).isNotEqualTo(actualOrder.getPrice()),
                () -> assertThat(expectedProduct.getRemainingQuantity()).isNotEqualTo(actualOrder.getRemainingQuantity())
        );
    }

    @Test
    void shouldDeleteExistingEntity() {
        session.delete(TestObjectUtils.PRODUCT);

        Product product = session.get(Product.class, IntegrationTestBase.EXISTING_ID);

        assertThat(product).isNull();
    }
}

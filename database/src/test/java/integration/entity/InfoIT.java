package integration.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import entity.Info;
import integration.IntegrationTestBase;
import util.TestObjectUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

public class InfoIT extends IntegrationTestBase {

    public static final Info INFO = Info.builder()
            .phoneNumber("+994708338308")
            .address("someAddress")
            .build();

    private final Session session = HibernateUtil.buildSession();


    @Test
    void shouldSaveCorrectEntity() {
        var expectedInfo = INFO;

        session.save(expectedInfo);
        Info actualInfo = session.get(Info.class, 2);

        assertThat(actualInfo).isNotNull();
        assertThat(expectedInfo.getPhoneNumber()).isEqualTo(actualInfo.getPhoneNumber());
    }

    @Test
    void shouldFindExistingEntity() {
        Info info = session.get(Info.class, IntegrationTestBase.EXISTING_ID);
        assertThat(info).isNotNull();
    }

    @Test
    void shouldReturnEmptyIfEntityDoesNotExist() {
        Info info = session.get(Info.class, IntegrationTestBase.NON_EXISTENT_ID);
        assertThat(info).isNull();
    }

    @Test
    void shouldUpdateExistingEntity() {
        var expectedInfo = INFO;
        expectedInfo.setAddress("anotherAddress");
        expectedInfo.setPhoneNumber("+994954543532");

        session.saveOrUpdate(expectedInfo);
        Info actualInfo = session.get(Info.class, IntegrationTestBase.EXISTING_ID);

        assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(expectedInfo).isNotEqualTo(actualInfo),
                () -> assertThat(expectedInfo.getAddress()).isNotEqualTo(actualInfo.getAddress()),
                () -> assertThat(expectedInfo.getAddress()).isNotEqualTo(actualInfo.getPhoneNumber())
        );
    }

    @Test
    void shouldDeleteExistingEntity() {
        session.delete(TestObjectUtils.INFO);

        Info info = session.get(Info.class, IntegrationTestBase.EXISTING_ID);

        assertThat(info).isNull();
    }


}

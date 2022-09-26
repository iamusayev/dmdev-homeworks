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
        Info actualInfo;
        
        try(session){
        session.beginTransaction();
        session.save(expectedInfo);
        actualInfo = session.get(Info.class, TestObjectUtils.JUST_CREATED_ID);
        session.getTransaction().commit();
        }

        assertThat(actualInfo).isNotNull();
        assertThat(expectedInfo.getPhoneNumber()).isEqualTo(actualInfo.getPhoneNumber());
    }

    @Test
    void shouldFindExistingEntity() {
        Info actualInfo;
        
        try(session){
        session.beginTransaction();
        info = session.get(Info.class, TestObjectUtils.EXISTING_ID);
        session.getTransaction().commit(); 
        }
        
        assertThat(info).isNotNull();
    }

    @Test
    void shouldReturnEmptyIfEntityDoesNotExist() {
        Info actualInfo;
    
        try(session){
        session.beginTransaction();
        info = session.get(Info.class, TestObjectUtils.EXISTING_ID);
        session.getTransaction().commit(); 
        }
        
        assertThat(info).isNull();
    }

    @Test
    void shouldUpdateExistingEntity() {
        var expectedInfo = INFO;
        Info actualInfo;
        expectedInfo.setAddress("anotherAddress");
        expectedInfo.setPhoneNumber("+994954543532");

        try(session){
        session.beginTransaction();
        session.saveOrUpdate(expectedInfo);
        actualInfo = session.get(Info.class, TestObjectUtils.EXISTING_ID); 
        session.getTransaction().commit(); 
        }

        assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(expectedInfo).isNotEqualTo(actualInfo),
                () -> assertThat(expectedInfo.getAddress()).isNotEqualTo(actualInfo.getAddress()),
                () -> assertThat(expectedInfo.getAddress()).isNotEqualTo(actualInfo.getPhoneNumber())
        );
    }

    @Test
    void shouldDeleteExistingEntity() {
        Info info;
        
        try(session){
        session.beginTransaction();
        session.delete(TestObjectUtils.INFO);
        info = session.get(Info.class, TestObjectUtils.EXISTING_ID);
        session.getTransaction().commit(); 
        }
       
        assertThat(info).isNull();
    }
}

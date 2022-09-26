package util;

import entity.Customer;
import entity.Info;
import entity.Order;
import entity.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;
import model.Status;

@UtilityClass
public class TestObjectUtils {

    public static final Customer CUSTOMER = Customer.builder()
            .surname("test")
            .password("test1337")
            .firstName("test")
            .email("test@gmail.com")
            .build();

    public static final Info INFO = Info.builder()
            .phoneNumber("+994516089260")
            .address("test")
            .build();

    public static final Order ORDER = Order.builder()
            .registrationDate(LocalDate.of(2020,1,11))
            .closingDate(LocalDate.of(2020,1,15))
            .status(Status.ACTIVE)
            .build();

    public static final Product PRODUCT = Product.builder()
            .name("test")
            .description("test")
            .price(BigDecimal.valueOf(16,2))
            .remainingQuantity(10)
            .build();

}
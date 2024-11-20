package test.entity;

import doa_jewelry.entity.Order;
import doa_jewelry.entity.OrderStatus;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class OrderEntityTest {

    @Test
    public void testOrderInitialization() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(2L);
        order.setDate(LocalDate.now());
        order.setItems(Arrays.asList(
                new Order.Item(3L, 5),
                new Order.Item(4L, 2)
        ));
        order.setTotalAmount(1000.0);
        order.setStatus(OrderStatus.PENDING);

        assertEquals(Long.valueOf(1L), order.getId());
        assertEquals(Long.valueOf(2L), order.getCustomerId());
        assertEquals(2, order.getItems().size());
        assertEquals(1000.0, order.getTotalAmount(), 0.01);
        assertEquals(OrderStatus.PENDING, order.getStatus());
    }
}

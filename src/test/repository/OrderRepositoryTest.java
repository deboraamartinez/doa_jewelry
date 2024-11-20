package test.repository;

import doa_jewelry.entity.Order;
import doa_jewelry.entity.OrderStatus;
import doa_jewelry.repository.OrderRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OrderRepositoryTest {

    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        orderRepository = new OrderRepository();
        orderRepository.deleteAll();
    }

    @After
    public void tearDown() {
        orderRepository.deleteAll(); 
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setDate(LocalDate.now());
        order.setItems(Arrays.asList(
                new Order.Item(1L, 2),
                new Order.Item(2L, 1)
        ));
        order.setTotalAmount(1000.0);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        assertNotNull(savedOrder.getId());
        assertEquals(1, orderRepository.findAll().size());
    }

    @Test
    public void testFindById() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setDate(LocalDate.now());
        order.setItems(Arrays.asList(
                new Order.Item(1L, 2),
                new Order.Item(2L, 1)
        ));
        order.setTotalAmount(1000.0);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        Order retrievedOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        assertNotNull(retrievedOrder);
        assertEquals(savedOrder.getId(), retrievedOrder.getId());
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setDate(LocalDate.now());
        order.setItems(Arrays.asList(
                new Order.Item(1L, 2),
                new Order.Item(2L, 1)
        ));
        order.setTotalAmount(1000.0);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        orderRepository.deleteById(savedOrder.getId());

        assertTrue(orderRepository.findAll().isEmpty());
    }
}

package com.order.service;

import com.order.models.Order;
import com.order.repository.OrderRepository;
import com.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setOrderNumber("ORDER123");
        order.setOrderDate(Date.valueOf("2024-11-17"));
        order.setTotalAmount(100.0);
    }

    @Test
    void testSaveOrder() {
        when(orderRepository.saveOrder(any(Order.class))).thenReturn(1L);

        Long orderId = orderService.saveOrder(order);

        verify(orderRepository, times(1)).saveOrder(order);
        assertEquals(1L, orderId);
    }

    @Test
    void testFindOrderById() {
        Long orderId = 1L;
        when(orderRepository.findOrderById(orderId)).thenReturn(order);

        Order result = orderService.findOrderById(orderId);

        verify(orderRepository, times(1)).findOrderById(orderId);
        assertNotNull(result);
        assertEquals("ORDER123", result.getOrderNumber());
    }

    @Test
    void testFindByDateAndMinAmount() {
        Date date = Date.valueOf("2024-11-17");
        Double minAmount = 50.0;
        when(orderRepository.findByDateAndMinAmount(date, minAmount)).thenReturn(Collections.singletonList(order));

        List<Order> orders = orderService.findByDateAndMinAmount(date, minAmount);

        verify(orderRepository, times(1)).findByDateAndMinAmount(date, minAmount);
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("ORDER123", orders.get(0).getOrderNumber());
    }

    @Test
    void testFindByExcludingProduct() {
        String productName = "Product A";
        Date startDate = Date.valueOf("2024-11-01");
        Date endDate = Date.valueOf("2024-11-17");
        when(orderRepository.findByExcludingProduct(productName, startDate, endDate))
            .thenReturn(Collections.singletonList(order));

        List<Order> orders = orderService.findByExcludingProduct(productName, startDate, endDate);

        verify(orderRepository, times(1)).findByExcludingProduct(productName, startDate, endDate);
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("ORDER123", orders.get(0).getOrderNumber());
    }
}

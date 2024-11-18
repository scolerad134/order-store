package com.order.repository;

import com.order.models.Order;
import com.order.repository.impl.OrderRepositoryImpl;
import com.order.util.exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class OrderRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        order = new Order();
        order.setOrderNumber("12345");
        order.setTotalAmount(200.0);
        order.setOrderDate(Date.valueOf("2024-11-18"));
        order.setRecipient("John Doe");
        order.setDeliveryAddress("123 Elm St");
        order.setPaymentType("Credit");
        order.setDeliveryType("Express");
    }

    @Test
    void testFindOrderById_found() {
        String sql = "SELECT * FROM orders WHERE id = ?";
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setOrderNumber("12345");
        when(jdbcTemplate.queryForObject(eq(sql), any(Object[].class), any(RowMapper.class)))
            .thenReturn(mockOrder);

        Order actualOrder = orderRepository.findOrderById(1L);

        assert actualOrder != null;
        assert actualOrder.getOrderNumber().equals("12345");
    }

    @Test
    void testFindOrderById_notFound() {
        String sql = "SELECT * FROM orders WHERE id = ?";
        when(jdbcTemplate.queryForObject(eq(sql), any(Object[].class), any(RowMapper.class)))
            .thenThrow(new EmptyResultDataAccessException(1));

        try {
            orderRepository.findOrderById(1L);
        } catch (OrderNotFoundException e) {
            assert e.getMessage().contains("Order with id = 1 not found");
        }
    }

    @Test
    void testFindByDateAndMinAmount() {
        String sql = "SELECT * FROM orders WHERE order_date = ? AND total_amount > ?";
        List<Order> mockOrders = Arrays.asList(order);
        when(jdbcTemplate.query(eq(sql), any(Object[].class), any(RowMapper.class)))
            .thenReturn(mockOrders);

        List<Order> orders = orderRepository.findByDateAndMinAmount(Date.valueOf("2024-11-18"), 100.0);

        assert orders.size() == 1;
        assert orders.get(0).getOrderNumber().equals("12345");
    }

    @Test
    void testFindByExcludingProduct() {
        String sql = "SELECT * FROM orders INNER JOIN order_details ON orders.id = order_details.order_id WHERE order_details.product_name != ? AND orders.order_date BETWEEN ? AND ?";
        List<Order> mockOrders = Arrays.asList(order);
        when(jdbcTemplate.query(eq(sql), any(Object[].class), any(RowMapper.class)))
            .thenReturn(mockOrders);

        List<Order> orders = orderRepository.findByExcludingProduct("ProductA", Date.valueOf("2024-11-01"), Date.valueOf("2024-11-30"));

        assert orders.size() == 1;
        assert orders.get(0).getOrderNumber().equals("12345");
    }
}


package com.order.repository;

import com.order.models.OrderDetails;
import com.order.repository.impl.OrderDetailsRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.*;


import org.mockito.MockitoAnnotations;

public class OrderDetailsRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrderDetailsRepositoryImpl orderDetailsRepository;

    private OrderDetails orderDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderDetails = new OrderDetails();
        orderDetails.setProductCode(123L);
        orderDetails.setProductName("Product Name");
        orderDetails.setQuantity(10);
        orderDetails.setUnitPrice(100.0);
        orderDetails.setOrderId(1L);
    }

    @Test
    void saveOrderDetails_shouldExecuteJdbcTemplateUpdate() {
        String sql = "INSERT INTO order_details(product_code, product_name, quantity, unit_price, order_id) " +
            "VALUES(?, ?, ?, ?, ?)";

        orderDetailsRepository.saveOrderDetails(orderDetails);

        verify(jdbcTemplate, times(1)).update(eq(sql), eq(orderDetails.getProductCode()),
            eq(orderDetails.getProductName()), eq(orderDetails.getQuantity()),
            eq(orderDetails.getUnitPrice()), eq(orderDetails.getOrderId()));
    }

    @Test
    void saveOrderDetails_shouldHandleNullValues() {
        OrderDetails orderDetailsWithNulls = new OrderDetails();
        orderDetailsWithNulls.setProductCode(null);
        orderDetailsWithNulls.setProductName(null);
        orderDetailsWithNulls.setQuantity(0);
        orderDetailsWithNulls.setUnitPrice(0.0);
        orderDetailsWithNulls.setOrderId(1L);

        orderDetailsRepository.saveOrderDetails(orderDetailsWithNulls);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(null), eq(null), eq(0), eq(0.0), eq(1L));
    }

}

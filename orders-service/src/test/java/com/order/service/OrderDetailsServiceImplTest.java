package com.order.service;

import com.order.models.OrderDetails;
import com.order.repository.OrderDetailsRepository;
import com.order.service.impl.OrderDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderDetailsServiceImplTest {

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderDetailsServiceImpl orderDetailsService;

    private OrderDetails orderDetails;

    @BeforeEach
    void setUp() {
        orderDetails = new OrderDetails();
        orderDetails.setId(1L);
        orderDetails.setQuantity(5);
        orderDetails.setUnitPrice(100.0);
    }

    @Test
    void testSaveOrderDetails() {
        orderDetailsService.saveOrderDetails(orderDetails);

        verify(orderDetailsRepository, times(1)).saveOrderDetails(orderDetails);
    }

}

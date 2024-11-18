package com.order.facade;

import com.order.facade.impl.OrderFacadeImpl;
import com.order.openapi.model.OrderDto;
import com.order.mapper.OrderMapper;
import com.order.models.Order;
import com.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderFacadeImplTest {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderFacadeImpl orderFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        when(orderService.findOrderById(orderId)).thenReturn(order);
        when(orderMapper.toOrderDto(order)).thenReturn(orderDto);

        OrderDto result = orderFacade.getOrderById(orderId);

        assertEquals(orderDto, result);
        verify(orderService, times(1)).findOrderById(orderId);
        verify(orderMapper, times(1)).toOrderDto(order);
    }

    @Test
    void testGetOrdersByDateAndAmount() {
        Date date = Date.valueOf("2024-11-01");
        Double minAmount = 100.0;
        List<Order> orders = Arrays.asList(new Order(), new Order());
        List<OrderDto> orderDtos = Arrays.asList(new OrderDto(), new OrderDto());

        when(orderService.findByDateAndMinAmount(date, minAmount)).thenReturn(orders);
        when(orderMapper.toOrderDtoList(orders)).thenReturn(orderDtos);

        List<OrderDto> result = orderFacade.getOrdersByDateAndAmount(date, minAmount);

        assertEquals(orderDtos, result);
        verify(orderService, times(1)).findByDateAndMinAmount(date, minAmount);
        verify(orderMapper, times(1)).toOrderDtoList(orders);
    }

    @Test
    void testGetOrdersExcludingProduct() {
        String productName = "Product1";
        Date startDate = Date.valueOf("2024-11-01");
        Date endDate = Date.valueOf("2024-11-10");
        List<Order> orders = Arrays.asList(new Order(), new Order());
        List<OrderDto> orderDtos = Arrays.asList(new OrderDto(), new OrderDto());

        when(orderService.findByExcludingProduct(productName, startDate, endDate)).thenReturn(orders);
        when(orderMapper.toOrderDtoList(orders)).thenReturn(orderDtos);

        List<OrderDto> result = orderFacade.getOrdersExcludingProduct(productName, startDate, endDate);

        assertEquals(orderDtos, result);
        verify(orderService, times(1)).findByExcludingProduct(productName, startDate, endDate);
        verify(orderMapper, times(1)).toOrderDtoList(orders);
    }
}

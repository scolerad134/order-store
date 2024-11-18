package com.order.util;

import com.order.models.Order;
import com.order.models.OrderDetails;
import com.order.openapi.model.OrderDto;
import com.order.openapi.model.OrderInfoDto;
import com.order.openapi.model.OrderNumberDto;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static OrderInfoDto createOrderInfoDto() {
        return new OrderInfoDto(
            "John Doe",
            "123 Elm Street",
            "Credit Card",
            "Home Delivery",
            1001,
            "Laptop",
            2,
            750.99
        );
    }

    public static OrderInfoDto createOrderInfoDtoUncorrect() {
        return new OrderInfoDto(
            null,
            "123 Elm Street",
            "Credit Card",
            "Home Delivery",
            1001,
            "Laptop",
            2,
            750.99
        );
    }

    public static OrderDto createOrderDto() {
        return new OrderDto(
            123L,
            "ABC10",
            BigDecimal.TEN,
            Date.valueOf("2024-11-01").toLocalDate(),
            "Max",
            "Moscow",
            "VISA",
            "home"
        );
    }

    public static Order createOrder() {
        return new Order(
            123L,
            "ABC10",
            300.0,
            Date.valueOf("2024-11-01"),
            "Max",
            "Moscow",
            "VISA",
            "home"
        );
    }

    public static OrderDetails createOrderDetails() {
        return new OrderDetails(
            123L,
            100L,
            "Laptop",
            555,
            150.0,
            12L
        );
    }

    public static OrderNumberDto createOrderNumberDto() {
        return new OrderNumberDto(
            "ABC10",
            Date.valueOf("2024-11-01").toLocalDate()
        );
    }

    public static List<OrderDto> createOrderDtoList() {
        OrderDto orderDto1 = TestData.createOrderDto();
        OrderDto orderDto2 = TestData.createOrderDto();

        orderDto1.setId(1L);
        orderDto2.setId(2L);
        orderDto1.setOrderNumber("ORD-001");
        orderDto2.setOrderNumber("ORD-002");
        orderDto1.setTotalAmount(BigDecimal.valueOf(150));
        orderDto2.setTotalAmount(BigDecimal.valueOf(200));

        return List.of(orderDto1, orderDto2);
    }
}

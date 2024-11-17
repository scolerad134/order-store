package com.order.facade.impl;

import com.order.facade.OrderFacade;
import com.order.models.dtos.OrderDto;
import com.order.models.dtos.OrderNumber;
import com.order.models.entity.Order;
import com.order.models.entity.OrderDetails;
import com.order.service.OrderDetailsService;
import com.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Реализация интерфейса OrderFacade.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderFacadeImpl implements OrderFacade {

    // private final OrderMapper orderMapper;

    private final RestTemplate restTemplate;

    @Value("${number-generate-service.url}")
    private String numberGenerateServiceUrl;

    private final OrderService orderService;

    private final OrderDetailsService orderDetailsService;

    /**
     * Создание заказа.
     *
     * @param orderDto входные параметры для создания заказа
     *
     */
    @Override
    @Transactional
    public void createOrder(OrderDto orderDto) {
        log.debug("createOrder - start, orderDto = {}", orderDto);
        Order order = toOrder(orderDto);
        OrderNumber orderNumber = restTemplate.getForObject(numberGenerateServiceUrl, OrderNumber.class);
        order.setOrderNumber(orderNumber.getNumber());
        order.setOrderDate(orderNumber.getDate());
        Long orderId = orderService.saveOrder(order);
        OrderDetails orderDetails = toOrderDetails(orderDto);
        orderDetails.setOrderId(orderId);
        orderDetailsService.saveOrderDetails(orderDetails);
    }

    /**
     * Маппинг из OrderDto в Order.
     *
     * @param orderDto входные параметры с информацией о заказе
     * @return order заказ
     */
    private Order toOrder(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setTotalAmount(orderDto.quantity() * orderDto.unitPrice());
        order.setRecipient(orderDto.recipient());
        order.setDeliveryAddress(orderDto.deliveryAddress());
        order.setPaymentType(orderDto.paymentType());
        order.setDeliveryType(orderDto.deliveryType());

        return order;
    }

    /**
     * Маппинг из OrderDto в OrderDetails.
     *
     * @param orderDto входные параметры с информацией о заказе
     * @return orderDetails детали заказа
     */
    private OrderDetails toOrderDetails(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        OrderDetails orderDetails = new OrderDetails();

        orderDetails.setProductCode(orderDto.productCode());
        orderDetails.setProductName(orderDto.productName());
        orderDetails.setQuantity(orderDto.quantity());
        orderDetails.setUnitPrice(orderDto.unitPrice());

        return orderDetails;
    }
}

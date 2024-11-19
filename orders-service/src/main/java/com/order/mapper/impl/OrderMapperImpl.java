package com.order.mapper.impl;

import com.order.mapper.OrderMapper;
import com.order.models.Order;
import com.order.models.OrderDetails;
import com.order.openapi.model.OrderDto;
import com.order.openapi.model.OrderInfoDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class OrderMapperImpl implements OrderMapper {

    /**
     * Маппинг из OrderDto в Order.
     *
     * @param orderDto входные параметры с информацией о заказе
     * @return order заказ
     */
    @Override
    public Order toOrder(OrderInfoDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setRecipient(orderDto.getRecipient());
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setPaymentType(orderDto.getPaymentType());
        order.setDeliveryType(orderDto.getDeliveryType());

        return order;
    }

    /**
     * Маппинг из OrderDto в OrderDetails.
     *
     * @param orderDto входные параметры с информацией о заказе
     * @return orderDetails детали заказа
     */
    @Override
    public OrderDetails toOrderDetails(OrderInfoDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        OrderDetails orderDetails = new OrderDetails();

        orderDetails.setProductCode(Long.valueOf(orderDto.getProductCode()));
        orderDetails.setProductName(orderDto.getProductName());
        orderDetails.setQuantity(orderDto.getQuantity());
        orderDetails.setUnitPrice(orderDto.getUnitPrice());

        return orderDetails;
    }

    /**
     * Маппинг из Order в OrderDto.
     *
     * @param order входные параметры с информацией о заказе
     * @return orderDto детали заказа
     */
    @Override
    public OrderDto toOrderDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setTotalAmount(BigDecimal.valueOf(order.getTotalAmount()));
        orderDto.setOrderDate(order.getOrderDate().toLocalDate());
        orderDto.setRecipient(order.getRecipient());
        orderDto.setDeliveryAddress(order.getDeliveryAddress());
        orderDto.setPaymentType(order.getPaymentType());
        orderDto.setDeliveryType(order.getDeliveryType());

        return orderDto;
    }

    /**
     * Маппинг из list Order в list OrderDto.
     *
     * @param order входные параметры с информацией о заказе
     * @return list orderDto детали заказа
     */
    @Override
    public List<OrderDto> toOrderDtoList(List<Order> order) {
        if (order == null) {
            return null;
        }

        return order.stream()
            .map(this::toOrderDto)
            .toList();
    }
}

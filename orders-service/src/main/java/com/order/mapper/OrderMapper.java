package com.order.mapper;

import com.order.models.Order;
import com.order.models.OrderDetails;
import com.order.openapi.model.OrderDto;
import com.order.openapi.model.OrderInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

public interface OrderMapper {

    /**
     * Маппинг из OrderDto в Order.
     *
     * @param orderDto входные параметры с информацией о заказе
     * @return order заказ
     */
    Order toOrder(OrderInfoDto orderDto);

    /**
     * Маппинг из OrderDto в OrderDetails.
     *
     * @param orderDto входные параметры с информацией о заказе
     * @return orderDetails детали заказа
     */
    OrderDetails toOrderDetails(OrderInfoDto orderDto);

    /**
     * Маппинг из Order в OrderDto.
     *
     * @param order входные параметры с информацией о заказе
     * @return orderDto детали заказа
     */
    OrderDto toOrderDto(Order order);

    /**
     * Маппинг из list Order в list OrderDto.
     *
     * @param order входные параметры с информацией о заказе
     * @return list orderDto детали заказа
     */
    List<OrderDto> toOrderDtoList(List<Order> order);
}

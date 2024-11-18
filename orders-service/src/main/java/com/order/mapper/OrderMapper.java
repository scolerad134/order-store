package com.order.mapper;

import com.order.models.Order;
import com.order.models.OrderDetails;
import com.order.openapi.model.OrderDto;
import com.order.openapi.model.OrderInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface OrderMapper {

    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDtoList(List<Order> order);

    Order toOrder(OrderInfoDto orderInfoDto);

    OrderDetails toOrderDetails(OrderInfoDto orderDetails);
}

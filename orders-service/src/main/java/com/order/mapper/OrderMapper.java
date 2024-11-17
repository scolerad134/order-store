package com.order.mapper;

import com.order.models.entity.Order;
import com.order.models.entity.OrderDetails;
import com.order.openapi.model.OrderDto;
import com.order.openapi.model.OrderInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface OrderMapper {

    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDtoList(List<Order> order);

    Order toOrder(OrderInfoDto orderInfoDto);

    OrderDetails toOrderDetails(OrderInfoDto orderDetails);
}

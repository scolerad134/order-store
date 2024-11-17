// package com.order.mapper;
//
// import com.order.models.dtos.OrderDto;
// import com.order.models.entity.OrderDetails;
// import org.mapstruct.InjectionStrategy;
// import org.mapstruct.Mapper;
// import com.order.models.entity.Order;
// import org.mapstruct.Mapping;
// import java.time.LocalDate;
// import java.util.UUID;
//
//
// import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
//
// /**
//  * Маппер для сущности {@link Order}.
//  *
//  * @author Viacheslav Sles
//  */
// @Mapper(componentModel = "spring")
// public interface OrderMapper {
//
//     /**
//      * Маппинг из OrderDto -> Order.
//      *
//      * @param orderDto сущность OrderDto.
//      * @return Order
//      */
//     // @Mapping(target = "orderNumber", expression = "java(UUID.randomUUID())")
//     // @Mapping(target = "totalAmount", expression = "java(orderDto.quantity() * orderDto.unitPrice())")
//     // @Mapping(target = "orderDate", expression = "java(LocalDate.now())")
//     Order toOrder(OrderDto orderDto);
//
//     /**
//      * Маппинг из OrderDto -> OrderDetails.
//      *
//      * @param orderDto сущность OrderDto.
//      * @return OrderDetails
//      */
//     OrderDetails toOrderDetails(OrderDto orderDto);
// }

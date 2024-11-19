package com.order.facade.impl;

import com.order.facade.OrderFacade;
import com.order.mapper.OrderMapper;
import com.order.openapi.model.OrderDto;
import com.order.openapi.model.OrderInfoDto;
import com.order.openapi.model.OrderNumberDto;
import com.order.models.Order;
import com.order.models.OrderDetails;
import com.order.service.OrderDetailsService;
import com.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Реализация интерфейса OrderFacade.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderFacadeImpl implements OrderFacade {

    private final RestTemplate restTemplate;

    private final OrderMapper orderMapper;

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
    public void createOrder(OrderInfoDto orderDto) {
        log.debug("createOrder - start, orderDto = {}", orderDto);
        Order order = orderMapper.toOrder(orderDto);
        order.setTotalAmount(orderDto.getQuantity() * orderDto.getUnitPrice());
        OrderNumberDto orderNumber = restTemplate.getForObject(numberGenerateServiceUrl, OrderNumberDto.class);
        order.setOrderNumber(orderNumber.getNumber());
        order.setOrderDate(Date.valueOf(orderNumber.getDate()));
        Long orderId = orderService.saveOrder(order);
        OrderDetails orderDetails = orderMapper.toOrderDetails(orderDto);
        orderDetails.setOrderId(orderId);
        orderDetailsService.saveOrderDetails(orderDetails);
    }

    /**
     * Получение заказа.
     *
     * @param id идентификатор заказа
     *
     * @return order заказ
     *
     */
    @Override
    public OrderDto getOrderById(Long id) {
        log.debug("getOrderById - start, id = {}", id);
        Order order = orderService.findOrderById(id);
        log.debug("getOrderById - end, order = {}", order);
        return orderMapper.toOrderDto(order);
    }

    /**
     * Получение заказов по дате и минимальной сумме.
     *
     * @param date дата создания заказа
     * @param minAmount минимальная сумма заказа.
     *
     * @return order заказ
     */
    @Override
    public List<OrderDto> getOrdersByDateAndAmount(Date date, Double minAmount) {
        log.debug("getOrdersByDateAndAmount - start, date = {}, minAmount = {}", date, minAmount);
        List<Order> orderList = orderService.findByDateAndMinAmount(date, minAmount);
        log.debug("getOrdersByDateAndAmount - end, date = {}, minAmount = {}", date, minAmount);
        return orderMapper.toOrderDtoList(orderList);
    }

    /**
     * Получение заказов не содержащих заданный товар и поступивших в заданный временной
     * период.
     *
     * @param productName название товара
     * @param startDate, начало временного диапазона
     * @param endDate конец временного диапазона
     *
     * @return order заказ
     */
    @Override
    public List<OrderDto> getOrdersExcludingProduct(String productName, Date startDate, Date endDate) {
        log.debug("getOrdersExcludingProduct - start, productName = {}, minAmount = {}, endDate = {}",
            productName, startDate, endDate);

        List<Order> orderList = orderService.findByExcludingProduct(productName, startDate, endDate);

        log.debug("getOrdersExcludingProduct - end, productName = {}, minAmount = {}, endDate = {}",
            productName, startDate, endDate);

        return orderMapper.toOrderDtoList(orderList);
    }

}

package com.order.service.impl;

import com.order.models.entity.Order;
import com.order.repository.OrderRepository;
import com.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Реализация {@link OrderService}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    /**
     * Создание заказа.
     *
     * @param order входные параметры для создания заказа
     *
     * @return id созданного заказа
     */
    @Override
    public Long saveOrder(Order order) {
        log.debug("saveOrder - start, order = {}", order);
        Long orderId = orderRepository.saveOrder(order);
        log.debug("saveOrder - end, with orderId = {}", orderId);
        return orderId;
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
    public Order findOrderById(Long id) {
        log.debug("findOrderById - start, id = {}", id);
        Order order = orderRepository.findOrderById(id);
        log.debug("findOrderById - end, order = {}", order);
        return order;
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
    public List<Order> findByDateAndMinAmount(Date date, Double minAmount) {
        log.debug("findOrderById - start, date = {}, minAmount = {}", date, minAmount);
        List<Order> orderList = orderRepository.findByDateAndMinAmount(date, minAmount);
        log.debug("findOrderById - end, date = {}, minAmount = {}", date, minAmount);
        return orderList;
    }
}

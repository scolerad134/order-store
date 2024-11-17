package com.order.service;


import com.order.models.entity.Order;

import java.sql.Date;
import java.util.List;

/**
 * API сервиса для работы с {@link Order}.
 */
public interface OrderService {

    /**
     * Создание заказа.
     *
     * @param order входные параметры для создания заказа
     *
     * @return id созданного заказа
     */
    Long saveOrder(Order order);

    /**
     * Получение заказа.
     *
     * @param id идентификатор заказа
     *
     * @return order заказ
     *
     */
    Order findOrderById(Long id);

    /**
     * Получение заказов по дате и минимальной сумме.
     *
     * @param date дата создания заказа
     * @param minAmount минимальная сумма заказа.
     *
     * @return order заказ
     */
    List<Order> findByDateAndMinAmount(Date date, Double minAmount);
}

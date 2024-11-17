package com.order.service;


import com.order.models.entity.Order;

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
}

package com.order.repository;

import com.order.models.entity.Order;

/**
 * Репозиторий для работы с Order.
 */
public interface OrderRepository {

    /**
     * Метод для сохранения заказа.
     *
     * @param order Объект заказа.
     * @return id заказа.
     */
    Long saveOrder(Order order);

    /**
     * Получение заказа.
     *
     * @param id идентификатор заказа
     * @return order заказ
     *
     */
    Order findOrderById(Long id);
}

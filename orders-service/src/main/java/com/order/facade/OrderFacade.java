package com.order.facade;

import com.order.models.dtos.OrderDto;
import com.order.models.entity.Order;

/**
 * Фасад для работы с заказами.
 */
public interface OrderFacade {

    /**
     * Создание заказа.
     *
     * @param orderDto входные параметры для создания заказа
     *
     */
    void createOrder(OrderDto orderDto);

    /**
     * Получение заказа.
     *
     * @param id идентификатор заказа
     *
     * @return order заказ
     *
     */
    Order getOrderById(Long id);
}

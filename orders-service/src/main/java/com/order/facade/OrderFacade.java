package com.order.facade;

import com.order.models.dtos.OrderDto;


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
}

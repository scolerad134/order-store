package com.order.service;

import com.order.models.OrderDetails;

/**
 * API сервиса для работы с {@link OrderDetails}.
 */
public interface OrderDetailsService {
    /**
     * Создание деталей заказа.
     *
     * @param orderDetails входные параметры для создания заказа
     *
     */
    void saveOrderDetails(OrderDetails orderDetails);
}

package com.order.repository;

import com.order.models.entity.Order;
import com.order.models.entity.OrderDetails;

/**
 * Репозиторий для работы с OrderDetails.
 */
public interface OrderDetailsRepository {

    /**
     * Метод для сохранения деталей заказа.
     *
     * @param orderDetails Объект с деталями заказа.
     */
    void saveOrderDetails(OrderDetails orderDetails);
}

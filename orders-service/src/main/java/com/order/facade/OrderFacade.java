package com.order.facade;

import com.order.models.dtos.OrderDto;
import com.order.models.entity.Order;

import java.sql.Date;
import java.util.List;

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

    /**
     * Получение заказов по дате и минимальной сумме.
     *
     * @param date дата создания заказа
     * @param minAmount минимальная сумма заказа.
     *
     * @return order заказ
     */
    List<Order> getOrdersByDateAndAmount(Date date, Double minAmount);
}

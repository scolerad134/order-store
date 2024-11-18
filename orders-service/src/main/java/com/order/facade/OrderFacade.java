package com.order.facade;

import com.order.openapi.model.OrderDto;
import com.order.openapi.model.OrderInfoDto;

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
    void createOrder(OrderInfoDto orderDto);

    /**
     * Получение заказа.
     *
     * @param id идентификатор заказа
     *
     * @return order заказ
     *
     */
    OrderDto getOrderById(Long id);

    /**
     * Получение заказов по дате и минимальной сумме.
     *
     * @param date дата создания заказа
     * @param minAmount минимальная сумма заказа.
     *
     * @return order заказ
     */
    List<OrderDto> getOrdersByDateAndAmount(Date date, Double minAmount);

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
    List<OrderDto> getOrdersExcludingProduct(String productName, Date startDate, Date endDate);
}

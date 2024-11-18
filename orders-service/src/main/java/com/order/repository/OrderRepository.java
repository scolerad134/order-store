package com.order.repository;

import com.order.models.Order;

import java.sql.Date;
import java.util.List;

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

    /**
     * Получение заказов по дате и минимальной сумме.
     *
     * @param date дата создания заказа
     * @param minAmount минимальная сумма заказа.
     *
     * @return order заказ
     */
    List<Order> findByDateAndMinAmount(Date date, Double minAmount);

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
    List<Order> findByExcludingProduct(String productName, Date startDate, Date endDate);
}

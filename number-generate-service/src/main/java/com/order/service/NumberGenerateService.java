package com.order.service;

import com.order.models.OrderNumber;

import java.sql.Date;

/**
 * API сервиса для работы с {@link OrderNumber}.
 */
public interface NumberGenerateService {

    /**
     * Генерация номера заказа.
     *
     * @return id созданного заказа
     */
    String generateUniqueNumber();

    /**
     * Получение текущих даты и времени.
     *
     * @return дата и время
     */
    Date getCurrentDateTime();
}

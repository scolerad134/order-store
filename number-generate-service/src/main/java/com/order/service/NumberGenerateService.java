package com.order.service;

import com.order.openapi.model.OrderNumberDto;

import java.sql.Date;

/**
 * API сервиса для работы с {@link OrderNumberDto}.
 */
public interface NumberGenerateService {

    /**
     * Получение данных о заказе.
     *
     * @return dto c номером заказа и датой
     */
    OrderNumberDto getOrderData();
}

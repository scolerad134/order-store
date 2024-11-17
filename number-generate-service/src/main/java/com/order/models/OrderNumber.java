package com.order.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * Класс представляет собой номер заказов в системе.
 */
@Data
@AllArgsConstructor
public class OrderNumber {
    /**
     * Номер заказа.
     */
    private String number;

    /**
     * Текущие дата и время.
     */
    private Date date;
}

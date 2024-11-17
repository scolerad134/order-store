package com.order.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

/**
 * Класс представляет собой номер заказов в системе.
 */
@Data
@AllArgsConstructor
public class OrderNumberDto {
    /**
     * Номер заказа.
     */
    private String number;

    /**
     * Текущие дата и время.
     */
    private Date date;
}

package com.order.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;


/**
 * Класс представляет собой модель заказов в системе.
 * Учетная запись связана с заказом.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    /**
     * ID.
     */
    @Id
    private Long id;

    /**
     * Номер заказа.
     */
    private String orderNumber;

    /**
     * Общая сумма заказа.
     */
    private Double totalAmount;

    /**
     * Дата заказа.
     */
    private Date orderDate;

    /**
     * Получатель.
     */
    private String recipient;

    /**
     * Адрес доставки.
     */
    private String deliveryAddress;

    /**
     * Тип оплаты (карта, наличные).
     */
    private String paymentType;

    /**
     * Тип доставки (самовывоз, доставка до двери).
     */
    private String deliveryType;
}

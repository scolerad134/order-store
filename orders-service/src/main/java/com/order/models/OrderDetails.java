package com.order.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Класс представляет собой модель деталей заказа в системе.
 * Учетная запись связана с деталями заказа.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetails {

    /**
     * ID.
     */
    @Id
    private Long id;

    /**
     * Артикул товара.
     */
    private Long productCode;

    /**
     * Название товара .
     */
    private String productName;

    /**
     * Количество.
     */
    private Integer quantity;

    /**
     * Стоимость единицы товара.
     */
    private Double unitPrice;

    /**
     * ID заказа.
     */
    private Long orderId;
}

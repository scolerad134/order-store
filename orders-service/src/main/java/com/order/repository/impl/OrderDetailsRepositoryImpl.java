package com.order.repository.impl;

import com.order.models.OrderDetails;
import com.order.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Реализация {@link OrderDetailsRepository}.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Метод для сохранения деталей заказа.
     *
     * @param orderDetails Объект с деталями заказа.
     */
    @Override
    public void saveOrderDetails(OrderDetails orderDetails) {
        log.info("Save order details {}", orderDetails);
        String sql = "INSERT INTO order_details" +
            "(product_code, product_name, quantity, unit_price, order_id) " +
            "VALUES(?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, orderDetails.getProductCode(), orderDetails.getProductName(),
            orderDetails.getQuantity(), orderDetails.getUnitPrice(), orderDetails.getOrderId());
    }
}

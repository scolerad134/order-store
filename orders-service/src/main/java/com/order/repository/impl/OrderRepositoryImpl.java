package com.order.repository.impl;

import com.order.util.exception.OrderNotFoundException;
import com.order.models.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.order.repository.OrderRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Реализация {@link OrderRepository}.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Метод для сохранения заказа.
     *
     * @param order Объект заказа.
     * @return id заказа.
     */
    @Override
    public Long saveOrder(Order order) {
        log.info("Save order {}", order);
        String sql = "INSERT INTO orders " +
            "(order_number, total_amount, order_date, recipient, " +
            "delivery_address, payment_type, delivery_type) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(order.getOrderNumber()));
            ps.setDouble(2, order.getTotalAmount());
            ps.setDate(3, order.getOrderDate());
            ps.setString(4, order.getRecipient());
            ps.setString(5, order.getDeliveryAddress());
            ps.setString(6, order.getPaymentType());
            ps.setString(7, order.getDeliveryType());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    /**
     * Получение заказа.
     *
     * @param id идентификатор заказа
     * @return order заказ
     *
     */
    @Override
    public Order findOrderById(Long id) {
        log.info("Get order by id = {}", id);
        String sql = "SELECT * FROM orders WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString("order_number"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setRecipient(rs.getString("recipient"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setPaymentType(rs.getString("payment_type"));
                order.setDeliveryType(rs.getString("delivery_type"));
                return order;
            });
        } catch (EmptyResultDataAccessException e) {
            log.warn("Order with id = {} not found", id);
            throw new OrderNotFoundException("Order with id = " + id + " not found");
        }
    }

    /**
     * Получение заказов по дате и минимальной сумме.
     *
     * @param date дата создания заказа
     * @param minAmount минимальная сумма заказа.
     *
     * @return order заказ
     */
    @Override
    public List<Order> findByDateAndMinAmount(Date date, Double minAmount) {
        log.info("Get list of orders by date = {} and minAmount = {}", date, minAmount);
        String sql = "SELECT * FROM orders WHERE order_date = ? AND total_amount > ?";

        return jdbcTemplate.query(sql, new Object[]{date, minAmount}, (rs, rowNum) -> {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setOrderNumber(rs.getString("order_number"));
            order.setTotalAmount(rs.getDouble("total_amount"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setRecipient(rs.getString("recipient"));
            order.setDeliveryAddress(rs.getString("delivery_address"));
            order.setPaymentType(rs.getString("payment_type"));
            order.setDeliveryType(rs.getString("delivery_type"));
            return order;
        });
    }

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
    @Override
    public List<Order> findByExcludingProduct(String productName, Date startDate, Date endDate) {
        log.info("Get list of orders by exclude productName = {}", productName);
        String sql = "SELECT * FROM orders INNER JOIN order_details " +
            "ON orders.id = order_details.order_id " +
            "WHERE order_details.product_name != ? " +
            "AND orders.order_date BETWEEN ? AND ?";

        return jdbcTemplate.query(sql, new Object[]{productName, startDate, endDate}, (rs, rowNum) -> {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setOrderNumber(rs.getString("order_number"));
            order.setTotalAmount(rs.getDouble("total_amount"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setRecipient(rs.getString("recipient"));
            order.setDeliveryAddress(rs.getString("delivery_address"));
            order.setPaymentType(rs.getString("payment_type"));
            order.setDeliveryType(rs.getString("delivery_type"));
            return order;
        });
    }
}

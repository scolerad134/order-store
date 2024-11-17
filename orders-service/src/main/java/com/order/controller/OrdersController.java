package com.order.controller;

import com.order.models.dtos.OrderDto;
import com.order.facade.OrderFacade;
import com.order.models.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для работы с маппингом "/orders".
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final OrderFacade orderFacade;

    /**
     * Создание заказа.
     *
     * @param orderDto входные параметры для создания заказа
     *
     * @return id созданного заказа
     */
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        log.debug("POST-request, createOrder - start, order = {}", orderDto);
        orderFacade.createOrder(orderDto);
        log.debug("POST-request, createOrder - end, student = {}", orderDto);
        return ResponseEntity.ok("The order was successfully created");
    }

    /**
     * Получение заказа по id.
     *
     * @param id идентификатор заказа
     *
     * @return order заказ
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        log.debug("GET-request, getOrderById - start, id = {}", id);
        Order order = orderFacade.getOrderById(id);
        log.debug("GET-request, getOrderById - end, order = {}", order);
        return ResponseEntity.ok(order);
    }

    /**
     * Получение заказов по дате и минимальной сумме.
     *
     * @param date дата создания заказа
     * @param minAmount минимальная сумма заказа.
     *
     * @return order заказ
     */
    @GetMapping
    public ResponseEntity<List<Order>> getOrdersByDateAndAmount(
        @RequestParam Date date,
        @RequestParam(value = "minAmount", required = false) Double minAmount) {
        log.debug("GET-request, getOrdersByDateAndAmount - start, date = {}, minAmount = {}", date, minAmount);
        List<Order> orderList = orderFacade.getOrdersByDateAndAmount(date, minAmount);
        log.debug("GET-request, getOrdersByDateAndAmount - end, date = {}, minAmount = {}", date, minAmount);
        return ResponseEntity.ok(orderList);
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
    @GetMapping("/exclude")
    public ResponseEntity<List<Order>> getOrdersExcludingProduct(
        @RequestParam("productName") String productName,
        @RequestParam("startDate") Date startDate,
        @RequestParam("endDate") Date endDate) {
        log.debug("GET-request, getOrdersExcludingProduct - start, productName = {}, minAmount = {}, endDate = {}",
            productName, startDate, endDate);
        List<Order> orderList = orderFacade.getOrdersExcludingProduct(productName, startDate, endDate);
        log.debug("GET-request, getOrdersExcludingProduct - start, productName = {}, minAmount = {}, endDate = {}",
            productName, startDate, endDate);
        return ResponseEntity.ok(orderList);
    }

}

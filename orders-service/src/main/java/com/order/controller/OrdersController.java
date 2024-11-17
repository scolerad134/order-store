package com.order.controller;

import com.order.models.dtos.OrderDto;
import com.order.facade.OrderFacade;
import com.order.models.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping()
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
}

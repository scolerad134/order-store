package com.order.controller;

import com.order.openapi.model.OrderInfoDto;
import com.order.facade.OrderFacade;
import com.order.openapi.model.OrderDto;
import com.order.openapi.api.ApiApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

/**
 * Контроллер для работы с маппингом "/orders".
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController implements ApiApi {

    private final OrderFacade orderFacade;

    @Operation(summary = "Создать новый заказ", description = "Создаёт новый заказ с переданными данными.")
    @PostMapping
    public ResponseEntity<String> createOrder(
        @Valid
        @RequestBody
        @Parameter(
            description = "Объект с данными для создания заказа. Включает информацию о получателе, адресе доставки, типе оплаты и других параметрах.",
            required = true
        ) OrderInfoDto orderDto) {
        log.debug("POST-request, createOrder - start, order = {}", orderDto);
        orderFacade.createOrder(orderDto);
        log.debug("POST-request, createOrder - end, student = {}", orderDto);
        return ResponseEntity.ok("The order was successfully created");
    }

    @Operation(summary = "Получить заказ по ID", description = "Возвращает информацию о заказе по указанному идентификатору.")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(
        @PathVariable
        @Parameter(
            description = "Идентификатор заказа. Должен быть числом, уникальным для каждого заказа.",
            example = "123",
            required = true
        ) Long id) {
        log.debug("GET-request, getOrderById - start, id = {}", id);
        OrderDto order = orderFacade.getOrderById(id);
        log.debug("GET-request, getOrderById - end, order = {}", order);
        return ResponseEntity.ok(order);
    }

    @Operation(
        summary = "Получение заказов по дате и минимальной сумме",
        description = "Возвращает список заказов, созданных в указанную дату, с общей суммой больше минимальной."
    )
    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrdersByDateAndAmount(
        @RequestParam
        @Parameter(
            description = "Дата создания заказа (формат: yyyy-MM-dd)",
            example = "2024-11-17",
            required = true
        ) Date date,
        @RequestParam(value = "minAmount", required = false)
        @Parameter(
            description = "Минимальная сумма заказа. Если не указано, вернутся все заказы за указанную дату.",
            example = "100.0"
        ) Double minAmount) {
        log.debug("GET-request, getOrdersByDateAndAmount - start, date = {}, minAmount = {}", date, minAmount);
        List<OrderDto> orderList = orderFacade.getOrdersByDateAndAmount(date, minAmount);
        log.debug("GET-request, getOrdersByDateAndAmount - end, date = {}, minAmount = {}", date, minAmount);
        return ResponseEntity.ok(orderList);
    }

    @Operation(
        summary = "Получение заказов, не содержащих заданный товар",
        description = "Возвращает список заказов, не содержащих указанный товар, за заданный временной период."
    )
    @GetMapping("/exclude")
    public ResponseEntity<List<OrderDto>> getOrdersExcludingProduct(
        @RequestParam("productName")
        @Parameter(
            description = "Название товара, который должен быть исключён из заказов.",
            example = "Laptop",
            required = true
        ) String productName,
        @RequestParam("startDate")
        @Parameter(
            description = "Начало временного диапазона (формат: yyyy-MM-dd)",
            example = "2024-11-01",
            required = true
        ) Date startDate,
        @RequestParam("endDate")
        @Parameter(
            description = "Конец временного диапазона (формат: yyyy-MM-dd)",
            example = "2024-11-17",
            required = true
        ) Date endDate) {
        log.debug("GET-request, getOrdersExcludingProduct - start, productName = {}, minAmount = {}, endDate = {}",
            productName, startDate, endDate);
        List<OrderDto> orderList = orderFacade.getOrdersExcludingProduct(productName, startDate, endDate);
        log.debug("GET-request, getOrdersExcludingProduct - start, productName = {}, minAmount = {}, endDate = {}",
            productName, startDate, endDate);
        return ResponseEntity.ok(orderList);
    }

}

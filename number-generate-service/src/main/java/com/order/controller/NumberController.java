package com.order.controller;

import com.order.openapi.api.ApiApi;
import com.order.openapi.model.OrderNumberDto;
import com.order.service.NumberGenerateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Контроллер для работы с маппингом "/numbers".
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/numbers")
public class NumberController implements ApiApi {

    private final NumberGenerateService numberGenerateService;

    @Override
    @Operation(
        summary = "Генерация номера заказа",
        description = "Генерирует уникальный номер заказа и возвращает его вместе с текущей датой."
    )
    @GetMapping
    public ResponseEntity<OrderNumberDto> generateNumber() {
        log.debug("GET-request, generateNumber - start");
        OrderNumberDto orderNumberDto = numberGenerateService.getOrderData();
        log.debug("GET-request, generateNumber - end, orderNumberDto = {}", orderNumberDto);
        return ResponseEntity.ok(orderNumberDto);
    }
}

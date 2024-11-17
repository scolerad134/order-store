package com.order.controller;

import com.order.models.OrderNumber;
import com.order.service.NumberGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * Контроллер для работы с маппингом "/numbers".
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/numbers")
public class NumberController {

    private final NumberGenerateService numberGenerateService;

    /**
     * Генерация номера заказа.
     *
     *
     * @return orderNumber
     */
    @GetMapping()
    public ResponseEntity<OrderNumber> generateNumber() {
        String number = numberGenerateService.generateUniqueNumber();
        Date date = numberGenerateService.getCurrentDateTime();
        return ResponseEntity.ok(new OrderNumber(number, date));
    }
}

package com.order.models.dtos;

public record OrderDto(String recipient, String deliveryAddress, String paymentType,
                       String deliveryType, Long productCode, String productName,
                       Integer quantity, Double unitPrice) {}

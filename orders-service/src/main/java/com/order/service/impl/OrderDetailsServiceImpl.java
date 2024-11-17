package com.order.service.impl;

import com.order.models.entity.OrderDetails;
import com.order.repository.OrderDetailsRepository;
import com.order.service.OrderDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link OrderDetailsService}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    /**
     * Создание деталей заказа.
     *
     * @param orderDetails входные параметры для создания заказа
     *
     */
    @Override
    public void saveOrderDetails(OrderDetails orderDetails) {
        log.debug("saveOrderDetails - start, orderDetails = {}", orderDetails);
        orderDetailsRepository.saveOrderDetails(orderDetails);
        log.debug("saveOrderDetails - end, orderDetails = {}", orderDetails);
    }
}

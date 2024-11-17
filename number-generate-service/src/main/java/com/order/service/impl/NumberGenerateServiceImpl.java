package com.order.service.impl;

import com.order.service.NumberGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.UUID;

/**
 * Реализация {@link NumberGenerateService}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NumberGenerateServiceImpl implements NumberGenerateService {

    private static final String REDIS_KEY = "order-numbers";
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Генерация номера заказа.
     *
     * @return id созданного заказа
     */
    @Override
    public String generateUniqueNumber() {

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        String uniqueNumber = uuid.substring(0, 5).toUpperCase();

        Boolean isMember = redisTemplate.opsForSet().isMember(REDIS_KEY, uniqueNumber);

        if (Boolean.TRUE.equals(isMember)) {
            return generateUniqueNumber();
        }

        redisTemplate.opsForSet().add(REDIS_KEY, uniqueNumber);

        return uniqueNumber;
    }


    /**
     * Получение текущих даты и времени.
     *
     * @return дата и время
     */
    @Override
    public Date getCurrentDateTime() {
        return Date.valueOf(LocalDate.now());
    }
}

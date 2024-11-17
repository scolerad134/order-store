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

        if (Boolean.TRUE.equals(redisTemplate.hasKey(uniqueNumber))) {
            return generateUniqueNumber();
        }

        redisTemplate.opsForValue().set(uniqueNumber, UUID.randomUUID().toString());

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

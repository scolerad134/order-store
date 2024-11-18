package com.order.service;

import com.order.openapi.model.OrderNumberDto;
import com.order.service.impl.NumberGenerateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class NumberGenerateServiceImplTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private NumberGenerateServiceImpl numberGenerateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrderData_shouldReturnOrderNumberDto() {
        String generatedNumber = "ABCDE";
        when(redisTemplate.hasKey(generatedNumber)).thenReturn(false);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        OrderNumberDto result = numberGenerateService.getOrderData();

        assertNotNull(result);
        assertEquals(5, result.getNumber().length());
        assertNotNull(result.getDate());
    }

    @Test
    void generateUniqueNumber_shouldGenerateUniqueNumber() {
        String firstGeneratedNumber = "ABCDE";
        String secondGeneratedNumber = "XYZAB";
        when(redisTemplate.hasKey(firstGeneratedNumber)).thenReturn(true);
        when(redisTemplate.hasKey(secondGeneratedNumber)).thenReturn(false);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        String generatedNumber = numberGenerateService.generateUniqueNumber();

        assertNotNull(generatedNumber);
        assertEquals(5, generatedNumber.length());
        assertNotEquals(firstGeneratedNumber, generatedNumber);
    }

    @Test
    void getOrderData_shouldReturnUniqueNumberEachTime() {
        String firstGeneratedNumber = "ABCDE";
        String secondGeneratedNumber = "XYZAB";
        when(redisTemplate.hasKey(firstGeneratedNumber)).thenReturn(false);
        when(redisTemplate.hasKey(secondGeneratedNumber)).thenReturn(false);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        OrderNumberDto firstOrderData = numberGenerateService.getOrderData();
        OrderNumberDto secondOrderData = numberGenerateService.getOrderData();

        assertNotEquals(firstOrderData.getNumber(), secondOrderData.getNumber());
    }
}


package com.order.controller;


import com.order.openapi.model.OrderNumberDto;
import com.order.service.NumberGenerateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NumberControllerTest {

    @Mock
    private NumberGenerateService numberGenerateService;

    @InjectMocks
    private NumberController numberController;

    private MockMvc mockMvc;

    private OrderNumberDto orderNumberDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderNumberDto = new OrderNumberDto();
        orderNumberDto.setNumber("12345");
        orderNumberDto.setDate(LocalDate.parse("2024-11-18"));

        mockMvc = MockMvcBuilders.standaloneSetup(numberController).build();
    }

    @Test
    void generateNumber_shouldHandleNullOrderNumber() throws Exception {
        when(numberGenerateService.getOrderData()).thenReturn(null);

        mockMvc.perform(get("/api/v1/numbers")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.number").doesNotExist())
            .andExpect(jsonPath("$.date").doesNotExist());

        verify(numberGenerateService, times(1)).getOrderData();
    }

}

package com.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.facade.OrderFacade;
import com.order.openapi.model.OrderDto;
import com.order.openapi.model.OrderInfoDto;
import com.order.util.TestData;
import com.order.util.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderFacade orderFacade;

    @Test
    void createOrder_ValidRequest_ShouldReturn200() throws Exception {
        OrderInfoDto orderInfoDto = TestData.createOrderInfoDto();

        doNothing().when(orderFacade).createOrder(Mockito.any(OrderInfoDto.class));

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderInfoDto)))
            .andExpect(status().isOk())
            .andExpect(content().string("The order was successfully created"));
    }

    @Test
    void createOrder_InvalidRequest_ShouldReturn400() throws Exception {
        OrderInfoDto orderInfoDto = TestData.createOrderInfoDtoUncorrect();

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderInfoDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrderById_ValidId_ShouldReturn200() throws Exception {
        Long orderId = 123L;
        OrderDto orderDto = TestData.createOrderDto();

        when(orderFacade.getOrderById(orderId)).thenReturn(orderDto);

        mockMvc.perform(get("/api/v1/orders/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderDto.getId()))
            .andExpect(jsonPath("$.orderNumber").value(orderDto.getOrderNumber()))
            .andExpect(jsonPath("$.totalAmount").value(orderDto.getTotalAmount()))
            .andExpect(jsonPath("$.orderDate").value(orderDto.getOrderDate().toString()))
            .andExpect(jsonPath("$.recipient").value(orderDto.getRecipient()))
            .andExpect(jsonPath("$.deliveryAddress").value(orderDto.getDeliveryAddress()))
            .andExpect(jsonPath("$.paymentType").value(orderDto.getPaymentType()))
            .andExpect(jsonPath("$.deliveryType").value(orderDto.getDeliveryType()));
    }

    @Test
    void getOrderById_NonExistentId_ShouldReturn404() throws Exception {
        Long orderId = 999L;

        when(orderFacade.getOrderById(orderId)).thenThrow(new OrderNotFoundException("Order with id = " + orderId + " not found"));

        mockMvc.perform(get("/api/v1/orders/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void getOrderById_InvalidId_ShouldReturn400() throws Exception {
        String invalidId = "invalid";

        mockMvc.perform(get("/api/v1/orders/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByDateAndAmount_ValidRequest_ShouldReturn200() throws Exception {
        Date date = Date.valueOf("2024-11-17");
        Double minAmount = 100.0;

        List<OrderDto> orderList = TestData.createOrderDtoList();

        when(orderFacade.getOrdersByDateAndAmount(date, minAmount)).thenReturn(orderList);

        mockMvc.perform(get("/api/v1/orders")
                .param("date", date.toString())
                .param("minAmount", minAmount.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].orderNumber").value("ORD-001"))
            .andExpect(jsonPath("$[0].totalAmount").value(150.0))
            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].orderNumber").value("ORD-002"))
            .andExpect(jsonPath("$[1].totalAmount").value(200.0));
    }

    @Test
    void getOrdersByDateAndAmount_NoOrdersFound_ShouldReturn200WithEmptyList() throws Exception {
        Date date = Date.valueOf("2024-11-17");
        Double minAmount = 500.0;

        when(orderFacade.getOrdersByDateAndAmount(date, minAmount)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/orders")
                .param("date", date.toString())
                .param("minAmount", minAmount.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
    }

    @Test
    void getOrdersByDateAndAmount_InvalidDate_ShouldReturn400() throws Exception {
        String invalidDate = "invalid-date";

        mockMvc.perform(get("/api/v1/orders")
                .param("date", invalidDate)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersExcludingProduct_ValidRequest_ShouldReturn200() throws Exception {
        String productName = "Laptop";
        Date startDate = Date.valueOf("2024-11-01");
        Date endDate = Date.valueOf("2024-11-17");

        List<OrderDto> orderList = TestData.createOrderDtoList();

        when(orderFacade.getOrdersExcludingProduct(productName, startDate, endDate)).thenReturn(orderList);

        mockMvc.perform(get("/api/v1/orders/exclude")
                .param("productName", productName)
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].orderNumber").value("ORD-001"))
            .andExpect(jsonPath("$[0].totalAmount").value(150.0))
            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].orderNumber").value("ORD-002"))
            .andExpect(jsonPath("$[1].totalAmount").value(200.0));
    }

    @Test
    void getOrdersExcludingProduct_NoOrdersFound_ShouldReturn200WithEmptyList() throws Exception {
        String productName = "Laptop";
        Date startDate = Date.valueOf("2024-11-01");
        Date endDate = Date.valueOf("2024-11-17");

        when(orderFacade.getOrdersExcludingProduct(productName, startDate, endDate)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/orders/exclude")
                .param("productName", productName)
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
    }

    @Test
    void getOrdersExcludingProduct_MissingProductName_ShouldReturn400() throws Exception {
        Date startDate = Date.valueOf("2024-11-01");
        Date endDate = Date.valueOf("2024-11-17");

        mockMvc.perform(get("/api/v1/orders/exclude")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersExcludingProduct_MissingDate_ShouldReturn400() throws Exception {
        String productName = "Laptop";

        mockMvc.perform(get("/api/v1/orders/exclude")
                .param("productName", productName)
                .param("endDate", Date.valueOf("2024-11-17").toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/v1/orders/exclude")
                .param("productName", productName)
                .param("startDate", Date.valueOf("2024-11-01").toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersExcludingProduct_InvalidDateFormat_ShouldReturn400() throws Exception {
        String productName = "Laptop";
        String invalidStartDate = "01-11-2024";
        String invalidEndDate = "17-11-2024";

        mockMvc.perform(get("/api/v1/orders/exclude")
                .param("productName", productName)
                .param("startDate", invalidStartDate)
                .param("endDate", invalidEndDate)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

}

package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.OrderStatusFilter;
import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.ports.inbound.OrderService;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.fixture.OrderFixture;
import com.fiap.challenge.food.fixture.OrderViewFixture;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private ViewMapper viewMapper;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void approvePaymentCallsService() throws Exception {
        long orderId = 1L;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/orders/" + orderId + "/payment/approval"));
        resultActions.andExpect(status().isCreated());

        verify(orderService).approvePayment(orderId);
    }

    @Test
    void getOrdersByStatusReturnsPageResult() throws Exception {
        OrderStatusFilter statusFilter = OrderStatusFilter.ALL;
        PageResult<Order> orderPage = new PageResult<>(List.of(OrderFixture.validOrder()), 1, 1, 1, 1);
        when(orderService.getAllByStatus(any(), anyInt(), anyInt())).thenReturn(orderPage);
        when(viewMapper.toOrderView(any())).thenReturn(OrderViewFixture.validOrderView());

        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "10")
                .param("status", statusFilter.name()))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
    }

    @Test
    void getOrdersByStatusWithInvalidPageReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "0")
                .param("size", "10")
                .param("status", OrderStatusFilter.ALL.name()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByStatusWithInvalidSizeReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "21")
                .param("status", OrderStatusFilter.ALL.name()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByStatusWithInvalidStatusReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "10")
                .param("status", "TODOS"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByStatusWithEmptyStatusReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "10")
                .param("status", ""))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersByStatusWithNullStatusReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("size", "10")
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void patchOrderTransitionReturnsOk() throws Exception {
        long orderId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/{orderId}/status/transition", orderId))
            .andExpect(status().isOk());

        verify(orderService).updateStatus(orderId);
    }

}

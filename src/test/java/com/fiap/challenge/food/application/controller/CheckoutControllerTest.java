package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.CheckoutMutation;
import com.fiap.challenge.food.application.response.OrderView;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.ports.inbound.CheckoutService;
import com.fiap.challenge.food.fixture.OrderFixture;
import com.fiap.challenge.food.fixture.OrderViewFixture;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @Mock
    private ViewMapper viewMapper;

    @InjectMocks
    private CheckoutController checkoutController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(checkoutController).build();
    }

    @Test
    void checkoutReturnsOrderView() throws Exception {
        CheckoutMutation checkoutMutation = new CheckoutMutation(123L);
        OrderView orderView = OrderViewFixture.validOrderView();
        Order order = OrderFixture.validOrder();

        when(checkoutService.checkout(any())).thenReturn(order);
        when(viewMapper.toOrderView(any())).thenReturn(orderView);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cartId\":\"123\"}"));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(jsonPath("$.cartId").value(orderView.cartId()))
            .andExpect(jsonPath("$.consumerId").value(orderView.consumerId()))
            .andExpect(jsonPath("$.status").value(orderView.status().name()))
            .andExpect(jsonPath("$.total").value(orderView.total().toString()))
            .andExpect(jsonPath("$.payment").exists())
            .andExpect(jsonPath("$.payment.type").value(orderView.payment().type().name()))
            .andExpect(jsonPath("$.payment.amount").value(orderView.payment().amount().toString()))
            .andExpect(jsonPath("$.payment.status").value(orderView.payment().status().name()))
            .andExpect(jsonPath("$.payment.qrCode").value(orderView.payment().qrCode()))
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.items[0].productId").value(orderView.items().getFirst().productId()))
            .andExpect(jsonPath("$.items[0].productName").value(orderView.items().getFirst().productName()))
            .andExpect(jsonPath("$.items[0].productCategory").value(orderView.items().getFirst().productCategory().name()))
            .andExpect(jsonPath("$.items[0].quantity").value(orderView.items().getFirst().quantity()))
            .andExpect(jsonPath("$.items[0].price").value("20.0"))
            .andExpect(jsonPath("$.items[0].subtotal").value("40.0"))
            .andExpect(jsonPath("$.waitingMinutes").value(orderView.waitingMinutes()));
    }

    @Test
    void checkoutWithInvalidCartIdReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cartId\":\"\"}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void checkoutNoBodyReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void checkoutWithEmptyBodyReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.CartItemMutation;
import com.fiap.challenge.food.application.request.CartMutation;
import com.fiap.challenge.food.application.response.CartView;
import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.ports.inbound.CartService;
import com.fiap.challenge.food.fixture.CartViewFixture;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartServiceMock;

    @Mock
    private ViewMapper viewMapperMock;

    @InjectMocks
    private CartController cartController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController)
            .build();
    }


    @Test
    void createCartSuccessfully() throws Exception {
        CartMutation cartMutation = new CartMutation(1L);
        Cart cart = new Cart();
        CartView cartView = CartViewFixture.aEmptyCartView();

        when(cartServiceMock.create(1L)).thenReturn(cart);
        when(viewMapperMock.toCartView(cart)).thenReturn(cartView);

        ResultActions resultActions = mockMvc.perform(post("/carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"consumerId\": 1}"));

        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.consumerId").value(1L))
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.total").value(0))
        ;

        verify(cartServiceMock, times(1)).create(1L);
        verify(viewMapperMock, times(1)).toCartView(cart);
    }

    @Test
    void createItemSuccessfully() throws Exception {
        CartItemMutation itemMutation = new CartItemMutation(1L, 2);
        Cart cart = new Cart();
        CartView cartView = CartViewFixture.aCartViewWithSandwich();

        when(cartServiceMock.addItem(1L, 1L, 2)).thenReturn(cart);
        when(viewMapperMock.toCartView(cart)).thenReturn(cartView);

        ResultActions resultActions = mockMvc.perform(post("/carts/1/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productId\": 1, \"quantity\": 2}"));

        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.consumerId").value(1L))
            .andExpect(jsonPath("$.items[0].productId").value(1L))
            .andExpect(jsonPath("$.items[0].productName").value("X-TUDO"))
            .andExpect(jsonPath("$.items[0].price").value("20.0"))
            .andExpect(jsonPath("$.items[0].quantity").value(2))
            .andExpect(jsonPath("$.items[0].subTotal").value("40.0"))
            .andExpect(jsonPath("$.total").value(40));

        verify(cartServiceMock, times(1)).addItem(1L, 1L, 2);
        verify(viewMapperMock, times(1)).toCartView(cart);
    }

    @Test
    void getCartSuccessfully() throws Exception {
        Cart cart = new Cart();
        CartView cartView = CartViewFixture.aCartViewWithSandwich();

        when(cartServiceMock.getById(1L)).thenReturn(cart);
        when(viewMapperMock.toCartView(cart)).thenReturn(cartView);

        ResultActions resultActions = mockMvc.perform(get("/carts/1"));

        resultActions.andExpect(status().isOk())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.consumerId").value(1L))
            .andExpect(jsonPath("$.items[0].productId").value(1L))
            .andExpect(jsonPath("$.items[0].productName").value("X-TUDO"))
            .andExpect(jsonPath("$.items[0].price").value("20.0"))
            .andExpect(jsonPath("$.items[0].quantity").value(2))
            .andExpect(jsonPath("$.items[0].subTotal").value("40.0"))
            .andExpect(jsonPath("$.total").value(40));

        verify(cartServiceMock, times(1)).getById(1L);
        verify(viewMapperMock, times(1)).toCartView(cart);
    }

    @Test
    void createItemWithNegativeQuantity() throws Exception {
        mockMvc.perform(post("/carts/1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\": 1, \"quantity\": -1}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createItemWithZeroQuantity() throws Exception {
        mockMvc.perform(post("/carts/1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\": 1, \"quantity\": 0}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createItemWithNullProductId() throws Exception {
        mockMvc.perform(post("/carts/1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\": null, \"quantity\": 0}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createCartWithoutConsumerId() throws Exception {
        mockMvc.perform(post("/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
            )
            .andExpect(status().isCreated());
    }
}

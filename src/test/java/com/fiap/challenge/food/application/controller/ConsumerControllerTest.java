package com.fiap.challenge.food.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.challenge.food.application.request.ConsumerMutation;
import com.fiap.challenge.food.application.response.ConsumerView;
import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.consumer.Consumer;
import com.fiap.challenge.food.domain.ports.inbound.ConsumerService;
import com.fiap.challenge.food.fixture.ConsumerFixture;
import com.fiap.challenge.food.fixture.ConsumerViewFixture;
import com.fiap.challenge.food.infrastructure.config.GlobalExceptionHandler;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ConsumerControllerTest {

    @Mock
    private ConsumerService consumerService;

    @Mock
    private ViewMapper viewMapper;

    @InjectMocks
    private ConsumerController consumerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(consumerController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void getConsumerByCpf_returnsConsumerView() throws Exception {
        Consumer consumer = ConsumerFixture.aConsumerWithId();
        String cpf = "909.532.740-04";
        ConsumerView consumerView = ConsumerViewFixture.aConsumerViewWithId();
        when(consumerService.findByCpf(cpf)).thenReturn(consumer);
        when(viewMapper.toConsumerView(consumer)).thenReturn(consumerView);

        ResultActions resultActions = mockMvc.perform(get("/consumers/"+cpf)
            .contentType(APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(consumerView.id()))
            .andExpect(jsonPath("$.name").value(consumerView.name()))
            .andExpect(jsonPath("$.email").value(consumerView.email()))
            .andExpect(jsonPath("$.cpf").value(consumerView.cpf()));

    }

    @Test
    void getAllConsumers_returnsPageResultOfConsumerView() throws Exception {
        Consumer consumer = ConsumerFixture.aConsumerWithId();
        ConsumerView consumerView = ConsumerViewFixture.aConsumerViewWithId();
        PageResult<Consumer> consumers = new PageResult<>(List.of(consumer), 1, 10, 1, 1);
        when(consumerService.findAllConsumer(anyInt(), anyInt())).thenReturn(consumers);
        when(viewMapper.toConsumerView(any(Consumer.class))).thenReturn(consumerView);

        ResultActions resultActions = mockMvc.perform(get("/consumers")
            .param("page", "1")
            .param("size", "10")
            .contentType(APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(consumerView.id()))
            .andExpect(jsonPath("$.content[0].name").value(consumerView.name()))
            .andExpect(jsonPath("$.content[0].email").value(consumerView.email()))
            .andExpect(jsonPath("$.content[0].cpf").value(consumerView.cpf()))
            .andExpect(jsonPath("$.pageNumber").value(1))
            .andExpect(jsonPath("$.pageSize").value(10))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void createConsumer_returnsConsumerView() throws Exception {
        ConsumerMutation consumerMutation = ConsumerViewFixture.aConsumerMutation();
        Consumer consumer = ConsumerFixture.aConsumerWithId();
        ConsumerView consumerView = ConsumerViewFixture.aConsumerViewWithId();
        when(viewMapper.toConsumer(consumerMutation)).thenReturn(consumer);
        when(consumerService.create(any(Consumer.class))).thenReturn(consumer);
        when(viewMapper.toConsumerView(consumer)).thenReturn(consumerView);

        ResultActions resultActions = mockMvc.perform(post("/consumers")
            .content(new ObjectMapper().writeValueAsString(consumerMutation))
            .contentType(APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(consumerView.id()))
            .andExpect(jsonPath("$.name").value(consumerView.name()))
            .andExpect(jsonPath("$.email").value(consumerView.email()))
            .andExpect(jsonPath("$.cpf").value(consumerView.cpf()));
    }


    @MethodSource("invalidConsumerWriteRequests")
    @ParameterizedTest(name = " should throw exception with {1}")
    public void createConsumer_returnsBadRequest_whenFieldIsMissing(ConsumerMutation consumerMutation, String fieldValidation, String errorMessage) throws Exception {

        String content = new ObjectMapper().writeValueAsString(consumerMutation);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/consumers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content)
        );

        resultActions.andExpect(status().isBadRequest())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(jsonPath("$.detail").value("Invalid request content"))
            .andExpect(jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.instance").value("/consumers"))
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.errors").isNotEmpty())
            .andExpect(jsonPath("$.errors[0]").value(errorMessage));
    }

    static Stream<Arguments> invalidConsumerWriteRequests() {
        return Stream.of(
            Arguments.of(ConsumerViewFixture.aConsumerMutationWithNullName(), "null name", "name: must not be empty"),
            Arguments.of(ConsumerViewFixture.aConsumerMutationWithEmptyName(), "empty name", "name: must not be empty"),
            Arguments.of(ConsumerViewFixture.aConsumerMutationWithNullCpf(), "null cpf", "cpf: must not be null"),
            Arguments.of(ConsumerViewFixture.aConsumerMutationWithEmptyCpf(), "empty cpf", "cpf: invalid Brazilian individual taxpayer registry number (CPF)"),
            Arguments.of(ConsumerViewFixture.aConsumerMutationWithInvalidCpf(), "invalid cpf", "cpf: invalid Brazilian individual taxpayer registry number (CPF)"),
            Arguments.of(ConsumerViewFixture.aConsumerMutationWithNullEmail(), "null email", "email: must not be empty"),
            Arguments.of(ConsumerViewFixture.aConsumerMutationWithEmptyEmail(), "empty email", "email: must not be empty")
        );
    }

}

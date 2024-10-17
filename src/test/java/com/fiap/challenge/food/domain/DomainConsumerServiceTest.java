package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.consumer.Consumer;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.ports.outbound.ConsumerRepository;
import com.fiap.challenge.food.fixture.ConsumerFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DomainConsumerServiceTest {

    @Mock
    private ConsumerRepository consumerRepository;

    @InjectMocks
    private DomainConsumerService domainConsumerService;

    @Test
    void findAllConsumer_returnsPageResult() {
        PageResult<Consumer> expectedPageResult = new PageResult<>(
            List.of(ConsumerFixture.aConsumerWithId()),
            1,
            10,
            1,
            1
        );
        when(consumerRepository.findAll(1, 10)).thenReturn(expectedPageResult);

        PageResult<Consumer> result = domainConsumerService.findAllConsumer(1, 10);

        assertEquals(expectedPageResult, result);
    }

    @Test
    void create_savesAndReturnsConsumer() {
        Consumer consumer = ConsumerFixture.aConsumer();
        Consumer consumerWithId = ConsumerFixture.aConsumerWithId();
        when(consumerRepository.save(consumer)).thenReturn(consumerWithId);

        Consumer result = domainConsumerService.create(consumer);

        assertEquals(consumerWithId, result);
    }

    @Test
    void findByCpf_returnsConsumer() {
        Consumer consumer = ConsumerFixture.aConsumerWithId();
        when(consumerRepository.findByCpf("123456789")).thenReturn(Optional.of(consumer));

        Consumer result = domainConsumerService.findByCpf("123456789");

        assertEquals(consumer, result);
    }

    @Test
    void findByCpf_throwsNotFoundException() {
        when(consumerRepository.findByCpf("123456789")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            domainConsumerService.findByCpf("123456789");
        });

        assertEquals("Consumer not found cpf=123456789", exception.getMessage());
        assertEquals("CONSUMER_NOT_FOUND", exception.getCode());
    }
}

package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.consumer.Consumer;
import com.fiap.challenge.food.fixture.ConsumerEntityFixture;
import com.fiap.challenge.food.fixture.ConsumerFixture;
import com.fiap.challenge.food.infrastructure.entity.ConsumerEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostgresConsumerRepositoryTest {

    @Mock
    private JpaConsumerRepository jpaConsumerRepositoryMock;

    @Mock
    private EntityMapper entityMapperMock;

    @InjectMocks
    private PostgresConsumerRepository target;

    @Test
    public void save_whenConsumerIsValid_shouldReturnConsumer() {
        Consumer consumer = ConsumerFixture.aConsumer();
        ConsumerEntity consumerEntity = ConsumerEntityFixture.aConsumerEntity();
        when(entityMapperMock.toConsumerEntity(consumer))
            .thenReturn(consumerEntity);

        ConsumerEntity consumerEntityWithId = ConsumerEntityFixture.aValidConsumerEntityWithId();
        when(jpaConsumerRepositoryMock.save(consumerEntity))
            .thenReturn(consumerEntityWithId);

        Consumer aConsumerWithId = ConsumerFixture.aConsumerWithId();
        when(entityMapperMock.toConsumer(consumerEntityWithId))
            .thenReturn(aConsumerWithId);

        Consumer saved = target.save(consumer);

        assertEquals(aConsumerWithId, saved);
    }

    @Test
    public void findByCpf_whenConsumerExists_shouldReturnConsumer() {
        String cpf = "12345678900";
        Consumer consumer = ConsumerFixture.aConsumer();
        when(jpaConsumerRepositoryMock.findByCpf(cpf))
            .thenReturn(Optional.of(ConsumerEntityFixture.aValidConsumerEntityWithId()));

        when(entityMapperMock.toConsumer(ConsumerEntityFixture.aValidConsumerEntityWithId()))
            .thenReturn(consumer);

        Optional<Consumer> found = target.findByCpf(cpf);

        assertEquals(consumer, found.get());
    }

    @Test
    public void findByCpf_whenConsumerDoesNotExist_shouldReturnEmpty() {
        String cpf = "12345678900";
        when(jpaConsumerRepositoryMock.findByCpf(cpf))
            .thenReturn(Optional.empty());

        Optional<Consumer> found = target.findByCpf(cpf);

        assertEquals(Optional.empty(), found);
    }

    @Test
    public void findAll_whenConsumersExist_shouldReturnPageOfConsumers() {
        int page = 1;
        int size = 10;
        when(jpaConsumerRepositoryMock.findAll(PageRequest.of(0, size, PostgresConsumerRepository.SORT_BY_NAME_ORDER_ASC)))
            .thenReturn(new PageImpl<>(List.of(ConsumerEntityFixture.aValidConsumerEntityWithId()), PageRequest.of(0, size), 1));

        Consumer consumer = ConsumerFixture.aConsumer();
        when(entityMapperMock.toConsumer(ConsumerEntityFixture.aValidConsumerEntityWithId()))
            .thenReturn(consumer);

        PageResult<Consumer> allConsumers = target.findAll(page, size);

        assertEquals(1, allConsumers.getTotalElements());
        assertEquals(1, allConsumers.getTotalPages());
        assertEquals(1, allConsumers.getContent().size());
        assertEquals(page, allConsumers.getPageNumber());
        assertEquals(size, allConsumers.getPageSize());
        assertEquals(consumer, allConsumers.getContent().getFirst());
    }

    @Test
    public void findAll_whenConsumersDoNotExist_shouldReturnEmptyPage() {
        int page = 1;
        int size = 10;
        when(jpaConsumerRepositoryMock.findAll(PageRequest.of(0, size, PostgresConsumerRepository.SORT_BY_NAME_ORDER_ASC)))
            .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, size), 0));

        PageResult<Consumer> allConsumers = target.findAll(page, size);

        assertEquals(0, allConsumers.getTotalElements());
        assertEquals(0, allConsumers.getTotalPages());
        assertEquals(page, allConsumers.getPageNumber());
        assertEquals(size, allConsumers.getPageSize());
        assertEquals(0, allConsumers.getContent().size());
    }

    @Test
    public void existsById_whenConsumerExists_shouldReturnTrue() {
        Long id = 1L;
        when(jpaConsumerRepositoryMock.existsById(id))
            .thenReturn(true);

        boolean exists = target.existsById(id);
        assertTrue(exists);
    }

    @Test
    public void findById_whenConsumerExists_shouldReturnConsumer() {
        Long id = 1L;
        Consumer consumer = ConsumerFixture.aConsumer();
        when(jpaConsumerRepositoryMock.findById(id))
            .thenReturn(Optional.of(ConsumerEntityFixture.aValidConsumerEntityWithId()));

        when(entityMapperMock.toConsumer(ConsumerEntityFixture.aValidConsumerEntityWithId()))
            .thenReturn(consumer);

        Optional<Consumer> found = target.findById(id);

        assertEquals(consumer, found.get());
    }

    @Test
    public void findById_whenConsumerDoesNotExist_shouldReturnEmpty() {
        Long id = 1L;
        when(jpaConsumerRepositoryMock.findById(id))
            .thenReturn(Optional.empty());

        Optional<Consumer> found = target.findById(id);

        assertEquals(Optional.empty(), found);
    }
}

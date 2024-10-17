package com.fiap.challenge.food.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.challenge.food.application.request.ProductMutation;
import com.fiap.challenge.food.application.response.ProductView;
import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductStatus;
import com.fiap.challenge.food.domain.ports.inbound.ProductService;
import com.fiap.challenge.food.fixture.ProductDtoFixture;
import com.fiap.challenge.food.fixture.ProductFixture;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService service;

    @Mock
    private ViewMapper mapper;

    @InjectMocks
    private ProductController controller;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void createProduct_createsAndReturnsProduct() throws Exception {
        Product product = ProductFixture.aProduct();
        ProductView productView = ProductDtoFixture.aProductDto();

        when(mapper.toProduct(any())).thenReturn(product);
        when(service.create(product)).thenReturn(product);
        when(mapper.toProductView(product)).thenReturn(productView);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/products")
            .contentType("application/json")
            .content("{ \"name\": \"Product 1\", \"description\": \"Description 1\", \"images\": [\"image1.jpg\"], \"price\": 10, \"category\": \"SANDWICH\" }"));


        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.images[0]").value(product.getImages().stream().findFirst().get()))
            .andExpect(jsonPath("$.price").value("10"))
            .andExpect(jsonPath("$.category").value("SANDWICH"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void findById_returnsProduct() throws Exception {
        Product product = ProductFixture.aProduct();
        ProductView productView = ProductDtoFixture.aProductDto();

        when(service.findById(1L)).thenReturn(product);
        when(mapper.toProductView(product)).thenReturn(productView);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/products/1"));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.images[0]").value(product.getImages().stream().findFirst().get()))
            .andExpect(jsonPath("$.price").value("10"))
            .andExpect(jsonPath("$.category").value("SANDWICH"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void findAll_returnsProducts() throws Exception {
        Product product = ProductFixture.aProduct();
        ProductView productView = ProductDtoFixture.aProductDto();

        PageResult<Product> pageResult = new PageResult<>(List.of(product), 1, 10, 1, 1);
        when(service.findAllByStatus(List.of(ProductStatus.ACTIVE), 1, 10)).thenReturn(pageResult);
        when(mapper.toProductView(product)).thenReturn(productView);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/products")
            .param("page", "1")
            .param("size", "10")
            .param("status", "ACTIVE"));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").exists())
            .andExpect(jsonPath("$.content[0].id").isNumber())
            .andExpect(jsonPath("$.content[0].name").value(product.getName()))
            .andExpect(jsonPath("$.content[0].description").value(product.getDescription()))
            .andExpect(jsonPath("$.content[0].images[0]").value(product.getImages().stream().findFirst().get()))
            .andExpect(jsonPath("$.content[0].price").value("10"))
            .andExpect(jsonPath("$.content[0].category").value("SANDWICH"))
            .andExpect(jsonPath("$.content[0].status").value("ACTIVE"))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.pageNumber").value(1))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.pageSize").value(10));
    }

    @Test
    void updateProduct_updatesAndReturnsProduct() throws Exception {
        Product product = ProductFixture.aProduct();
        ProductView productView = ProductDtoFixture.aProductDto();

        when(mapper.toProduct(any())).thenReturn(product);
        when(service.update(1L, product)).thenReturn(product);
        when(mapper.toProductView(product)).thenReturn(productView);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
            .contentType("application/json")
            .content("{ \"name\": \"Product 1\", \"description\": \"Description 1\", \"images\": [\"http://image1.jpg\"], \"price\": 10, \"category\": \"SANDWICH\" }"));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.images[0]").value(product.getImages().stream().findFirst().get()))
            .andExpect(jsonPath("$.price").value("10"))
            .andExpect(jsonPath("$.category").value("SANDWICH"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void deleteProduct_deletesProduct() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"));

        resultActions.andExpect(status().isNoContent());
    }

    @MethodSource("invalidProductWriteRequests")
    @ParameterizedTest(name = " should throw exception with {1}")
    public void createProduct_returnsBadRequest_whenFieldIsMissing(ProductMutation productMutation, String fieldValidation, String errorMessage) throws Exception {

        String content = objectMapper.writeValueAsString(productMutation);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content)
        );

        resultActions.andExpect(status().isBadRequest())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(jsonPath("$.detail").value("Invalid request content"))
            .andExpect(jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.instance").value("/products"))
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.errors").isNotEmpty())
            .andExpect(jsonPath("$.errors[0]").value(errorMessage));
    }

    static Stream<Arguments> invalidProductWriteRequests() {
        return Stream.of(
            Arguments.of(ProductDtoFixture.mutationWithNullCategory(), "null category", "category: must not be null"),
            Arguments.of(ProductDtoFixture.mutationWithEmptyImages(), "empty images", "images: must not be empty"),
            Arguments.of(ProductDtoFixture.mutationWithNullImages(), "null images", "images: must not be empty"),
            Arguments.of(ProductDtoFixture.mutationWithNullName(), "null name", "name: must not be null"),
            Arguments.of(ProductDtoFixture.mutationWithNullDescription(), "null description", "description: must not be null"),
            Arguments.of(ProductDtoFixture.mutationWithNullPrice(), "null price", "price: must not be null")
        );
    }
}

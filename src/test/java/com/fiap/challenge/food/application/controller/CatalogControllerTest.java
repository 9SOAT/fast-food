package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.response.ProductView;
import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductCategory;
import com.fiap.challenge.food.domain.ports.inbound.CatalogService;
import com.fiap.challenge.food.fixture.ProductFixture;
import com.fiap.challenge.food.fixture.ProductViewFixture;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CatalogControllerTest {

    @Mock
    private CatalogService catalogServiceMock;

    @Mock
    private ViewMapper viewMapperMock;

    @InjectMocks
    private CatalogController catalogController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(catalogController)
            .build();
    }

    @Test
    public void testGetByCategory() throws Exception {
        ProductCategory category = ProductCategory.SANDWICH;
        int page = 1;
        int size = 10;

        Product product = ProductFixture.aProduct();
        when(catalogServiceMock.findAllByCategory(category, page, size))
            .thenReturn(PageResult.<Product>builder().content(List.of(product)).totalPages(1)
                .totalElements(1).pageSize(size).pageNumber(1).build());

        ProductView productView = ProductViewFixture.aProductViewWithId();
        when(viewMapperMock.toProductView(product))
            .thenReturn(productView);

        ResultActions resultActions = mockMvc.perform(get("/catalog")
            .param("category", category.name())
            .param("page", "1")
            .param("size", "10")
            .contentType(APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(productView.id()))
            .andExpect(jsonPath("$.content[0].name").value(productView.name()))
            .andExpect(jsonPath("$.content[0].description").value(productView.description()))
            .andExpect(jsonPath("$.content[0].images[0]").value(productView.images().stream().findFirst().get()))
            .andExpect(jsonPath("$.content[0].price").value(productView.price()))
            .andExpect(jsonPath("$.content[0].category").value(productView.category().name()))
            .andExpect(jsonPath("$.content[0].status").value(productView.status().name()))
            .andExpect(jsonPath("$.totalElements").value("1"))
            .andExpect(jsonPath("$.pageNumber").value("1"))
            .andExpect(jsonPath("$.totalPages").value("1"))
            .andExpect(jsonPath("$.pageSize").value("10"));
    }

    @Test
    public void testGetById() throws Exception {
        Long id = 1L;

        Product product = ProductFixture.aProduct();
        when(catalogServiceMock.getById(id))
            .thenReturn(product);

        ProductView productView = ProductViewFixture.aProductViewWithId();
        when(viewMapperMock.toProductView(product))
            .thenReturn(productView);

        ResultActions resultActions = mockMvc.perform(get("/catalog/{id}", id)
            .contentType(APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(productView.id()))
            .andExpect(jsonPath("$.name").value(productView.name()))
            .andExpect(jsonPath("$.description").value(productView.description()))
            .andExpect(jsonPath("$.images[0]").value(productView.images().stream().findFirst().get()))
            .andExpect(jsonPath("$.price").value(productView.price()))
            .andExpect(jsonPath("$.category").value(productView.category().name()))
            .andExpect(jsonPath("$.status").value(productView.status().name()));
    }
}

package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.ProductMutation;
import com.fiap.challenge.food.application.request.ProductStatusFilter;
import com.fiap.challenge.food.application.response.ProductView;
import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.ports.inbound.ProductService;
import com.fiap.challenge.food.infrastructure.mapper.PageResultMapper;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    private final ViewMapper mapper;

    public ProductController(ProductService service, ViewMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ProductView createProduct(@Valid @RequestBody ProductMutation productMutation) {
        Product product = service.create(mapper.toProduct(productMutation));
        return mapper.toProductView(product);
    }

    @GetMapping("/{id}")
    public ProductView findById(@PathVariable Long id) {
        Product product = service.findById(id);
        return mapper.toProductView(product);
    }

    @GetMapping
    public PageResult<ProductView> findAll(@RequestParam @Min(1) int page,
                                           @Max(50) @RequestParam int size,
                                           @RequestParam(name = "status") ProductStatusFilter statusFilter) {
        PageResult<Product> products = service.findAllByStatus(statusFilter.getStatuses(), page, size);
        return PageResultMapper.transformContent(products, mapper::toProductView);
    }

    @PutMapping("/{id}")
    public ProductView updateProduct(@PathVariable Long id, @Valid @RequestBody ProductMutation productMutation) {
        Product product = service.update(id, mapper.toProduct(productMutation));
        return mapper.toProductView(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        service.delete(id);
    }
}

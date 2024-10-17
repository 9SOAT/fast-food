package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.response.ProductView;
import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductCategory;
import com.fiap.challenge.food.domain.ports.inbound.CatalogService;
import com.fiap.challenge.food.infrastructure.mapper.PageResultMapper;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;
    private final ViewMapper viewMapper;

    public CatalogController(CatalogService catalogService, ViewMapper viewMapper) {
        this.catalogService = catalogService;
        this.viewMapper = viewMapper;
    }

    @GetMapping(params = "category")
    public PageResult<ProductView> getByCategory(@RequestParam ProductCategory category, @RequestParam @Min(1) int page, @Max(50) @RequestParam int size) {
        PageResult<Product> allByCategory = catalogService.findAllByCategory(category, page, size);
        return PageResultMapper.transformContent(allByCategory, viewMapper::toProductView);
    }

    @GetMapping("/{id}")
    public ProductView getById(@PathVariable Long id) {
        Product product = catalogService.getById(id);
        return viewMapper.toProductView(product);
    }
}

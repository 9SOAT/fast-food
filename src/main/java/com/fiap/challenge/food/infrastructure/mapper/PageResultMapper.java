package com.fiap.challenge.food.infrastructure.mapper;

import com.fiap.challenge.food.domain.model.PageResult;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class PageResultMapper {

    public static <R, T> PageResult<R> toPageResult(Page<T> page, Function<T, R> mapper) {
        return PageResult.<R>builder()
            .content(page.getContent().stream().map(mapper).toList())
            .pageNumber(page.getPageable().getPageNumber() + 1)
            .pageSize(page.getPageable().getPageSize())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();
    }

    public static <T, R> PageResult<R> transformContent(PageResult<T> page, Function<T, R> mapper) {
        return PageResult.<R>builder()
            .content(page.getContent().stream().map(mapper).toList())
            .pageNumber(page.getPageNumber())
            .pageSize(page.getPageSize())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();
    }
}

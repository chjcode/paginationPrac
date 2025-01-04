package com.ssafy.paginationpractice.domain.board.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record BoardPaginatedResponseDto<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int currentPage,
        int size
) {
    public static <T> BoardPaginatedResponseDto from(Page<T> page) {
        return new BoardPaginatedResponseDto<>(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }
}

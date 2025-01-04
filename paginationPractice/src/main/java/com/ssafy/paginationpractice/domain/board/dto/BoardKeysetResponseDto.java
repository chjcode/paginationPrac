package com.ssafy.paginationpractice.domain.board.dto;

import java.util.List;

public record BoardKeysetResponseDto<T>(
        List<T> content,
        boolean hasNext,
        Long lastId
) {
    public static <T> BoardKeysetResponseDto<T> from(List<T> content, boolean hasNext, Long lastId) {
        return new BoardKeysetResponseDto<>(content, hasNext, lastId);
    }
}

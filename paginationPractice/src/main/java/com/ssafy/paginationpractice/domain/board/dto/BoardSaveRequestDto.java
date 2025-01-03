package com.ssafy.paginationpractice.domain.board.dto;

import com.ssafy.paginationpractice.domain.board.entity.Board;

public record BoardSaveRequestDto(
        String title,
        String content
) {
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}

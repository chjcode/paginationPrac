package com.ssafy.paginationpractice.domain.board.dto;

import com.ssafy.paginationpractice.domain.board.entity.Board;

public record BoardSaveResponseDto(
        Long boardId,
        String title,
        String content
) {
    public static BoardSaveResponseDto from(Board board) {
        return new BoardSaveResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent()
        );
    }
}

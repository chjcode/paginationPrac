package com.ssafy.paginationpractice.domain.board.dto;

import com.ssafy.paginationpractice.domain.board.entity.Board;

public record BoardPreviewDto(
        Long boardId,
        String title,
        String content,
        int commentCnt
) {
    public static BoardPreviewDto from(Board board, int commentCnt) {
        return new BoardPreviewDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                commentCnt
        );
    }
}

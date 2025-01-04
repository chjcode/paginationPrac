package com.ssafy.paginationpractice.domain.comment.dto;

import com.ssafy.paginationpractice.domain.board.entity.Board;
import com.ssafy.paginationpractice.domain.comment.entity.Comment;

public record CommentSaveRequestDto(
        String content,
        Long parentId
) {
    public Comment toEntity(Board board, Comment parentComment) {
        return Comment.builder()
                .board(board)
                .content(content)
                .parentComment(parentComment)
                .build();
    }
}

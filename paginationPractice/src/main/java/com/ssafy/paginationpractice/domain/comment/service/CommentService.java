package com.ssafy.paginationpractice.domain.comment.service;

import com.ssafy.paginationpractice.domain.board.dao.BoardDao;
import com.ssafy.paginationpractice.domain.board.entity.Board;
import com.ssafy.paginationpractice.domain.comment.dao.CommentDao;
import com.ssafy.paginationpractice.domain.comment.dto.CommentSaveRequestDto;
import com.ssafy.paginationpractice.domain.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    private final BoardDao boardDao;

    @Transactional
    public void save(Long boardId, CommentSaveRequestDto requestDto) {
        Board board = boardDao.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board Not Found"));
        Comment parentComment = getParentCommentById(requestDto.parentId());
        Comment comment = requestDto.toEntity(board, parentComment);
        commentDao.save(comment);
    }

    private Comment getParentCommentById(Long id) {
        if (id == null) {
            return null;
        }
        return getCommentById(id);
    }

    private Comment getCommentById(Long id) {
        return commentDao.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
    }

    public Page<Comment> findByBoardId(Long boardId, Pageable pageable) {

        Page<Comment> comments = commentDao.findByBoardId(boardId, pageable);
        return comments;
    }

    public void deleteById(Long commentId) {

    }
}

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
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    private final BoardDao boardDao;

    public void save(CommentSaveRequestDto commentSaveRequestDto) throws NoSuchElementException {
        Board board = boardDao.findById(commentSaveRequestDto.boardId())
                .orElseThrow(() -> new NoSuchElementException("Board Not Found"));
        Comment parent = commentDao.findById(commentSaveRequestDto.parentId())
                .orElseThrow(() -> new NoSuchElementException("Parent Not Found"));
        Comment comment = commentSaveRequestDto.toEntity(board, parent);
        commentDao.save(comment);
    }

    public Page<Comment> findByBoardId(Long boardId, Pageable pageable) {

        Page<Comment> comments = commentDao.findByBoardId(boardId, pageable);
        return comments;
    }

    public void deleteById(Long commentId) {

    }
}

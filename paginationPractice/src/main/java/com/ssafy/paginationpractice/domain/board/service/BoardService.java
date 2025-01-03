package com.ssafy.paginationpractice.domain.board.service;

import com.ssafy.paginationpractice.domain.board.dao.BoardDao;
import com.ssafy.paginationpractice.domain.board.dto.BoardSaveRequestDto;
import com.ssafy.paginationpractice.domain.board.dto.BoardSaveResponseDto;
import com.ssafy.paginationpractice.domain.board.entity.Board;
import com.ssafy.paginationpractice.domain.comment.dao.CommentDao;
import com.ssafy.paginationpractice.domain.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardDao boardDao;
    private final CommentDao commentDao;

    @Transactional
    public BoardSaveResponseDto save(BoardSaveRequestDto requestDto) {
        Board board = saveBoard(requestDto);

        return BoardSaveResponseDto.from(board);
    }

    /**
     * 성능 비교
     * 1. 재귀적으로 호출해가면서 하나씩 delete
     * 3. batch delete(orphanRemoval = true로 놓고 where = comment_Id 조건 삭제)
     */
    @Transactional
    public void delete(Long boardId) {
        Board board = getBoardById(boardId);

        removeBoardComments(board);
    }

    @Transactional
    public void deleteBatch(Long boardId) {
        Board board = getBoardById(boardId);

        removeBoardCommentsInBatch(board);
    }

    private Board saveBoard(BoardSaveRequestDto requestDto) {
        Board board = boardDao.save(requestDto.toEntity());
        return board;
    }

    private Board getBoardById(Long boardId) {
        return boardDao.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    private void removeBoardComments(Board board) {
        List<Comment> comments = board.getComments();
        for (Comment comment : comments) {
            removeChildComment(comment);
        }
    }

    private void removeChildComment(Comment parentComment) {
        List<Comment> childComments = parentComment.getChildComments();
        for (Comment childComment : childComments) {
            removeChildComment(childComment);
        }
        commentDao.delete(parentComment);
    }

    private void removeBoardCommentsInBatch(Board board) {
        List<Comment> comments = board.getComments();
        commentDao.deleteInBatch(comments);
    }
}

package com.ssafy.paginationpractice.domain.board.service;

import com.ssafy.paginationpractice.domain.board.dao.BoardDao;
import com.ssafy.paginationpractice.domain.board.dto.*;
import com.ssafy.paginationpractice.domain.board.entity.Board;
import com.ssafy.paginationpractice.domain.comment.dao.CommentDao;
import com.ssafy.paginationpractice.domain.comment.entity.Comment;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        removeBoard(board);
    }

    @Transactional
    public void deleteBatch(Long boardId) {
        Board board = getBoardById(boardId);

        removeBoardCommentsInBatch(board);
        removeBoard(board);
    }

    @Transactional
    public BoardPaginatedResponseDto<BoardPreviewDto> findAll(Pageable pageable) {
        Page<Board> boardPage = boardDao.findBoardWithPaging(pageable);

        List<Long> boardIds = boardPage.getContent()
                .stream()
                .map(Board::getId)
                .toList();

        List<Board> boardsWithComments = boardDao.findBoardWithCommentsByIds(boardIds);

        Map<Long, Board> boardMap = boardsWithComments.stream()
                .collect(Collectors.toMap(Board::getId, b -> b));

        // Board에서 BoardPreviewDto로 변환
        Page<BoardPreviewDto> previewDtoPage = boardPage.map(board -> {
            Board boardEntity = boardMap.get(board.getId());
            int commentCount = boardEntity.getComments().size();
            return BoardPreviewDto.from(board, commentCount);
        });

        return BoardPaginatedResponseDto.from(previewDtoPage);
    }

    @Transactional(readOnly = true)
    public BoardKeysetResponseDto<BoardPreviewDto> findAllByKeyset(Long lastId, int size) {
        // Keyset 특성상, 정렬은 id DESC 고정
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));

        // Board 엔티티 조회
        List<Board> boards = boardDao.findBoardByKeyset(lastId, pageable);

        // 1) 댓글 연관관계 로딩 (boardIds 따로 추출)
        List<Long> boardIds = boards.stream()
                .map(Board::getId)
                .toList();

        // 2) 한번에 comment fetch
        List<Board> boardsWithComments = boardDao.findBoardWithCommentsByIds(boardIds);

        // boardId -> Board 매핑
        Map<Long, Board> boardMap = boardsWithComments.stream()
                .collect(Collectors.toMap(Board::getId, b -> b));

        // 3) DTO 변환
        List<BoardPreviewDto> previewDtoList = boards.stream()
                .map(b -> {
                    Board boardEntity = boardMap.get(b.getId());
                    int commentCount = boardEntity.getComments().size();
                    return BoardPreviewDto.from(b, commentCount);
                })
                .toList();

        boolean hasNext = (previewDtoList.size() == size);
        Long nextLastId = previewDtoList.isEmpty()
                ? null
                : previewDtoList.get(previewDtoList.size()-1).boardId();

        return BoardKeysetResponseDto.from(previewDtoList, hasNext, nextLastId);
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

    private void removeBoard(Board board) {
        boardDao.delete(board);
    }
}

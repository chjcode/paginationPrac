package com.ssafy.paginationpractice.domain.comment.dao;

import com.ssafy.paginationpractice.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentDao extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    Page<Comment> findByBoardId(Long boardId, Pageable pageable);
    void deleteAllByBoardId(Long boardId);
    void deleteById(Long id);
}

/**
 * 20개씩 게시글 퍼올리는건데
 * -> 게시글 20개가 나오는데 각각의 게시글에 댓글개수가 달려있어야함
 * -> board에서 해결할 일임
 *
 * 게시글을 누르면 상세페이지가 나옴
 * -> board
 * -> comment -> 100개의 댓글이 있으면
 *              -> 10개씩~20개씩 갖고오는거지
 *
 *
 *
 */
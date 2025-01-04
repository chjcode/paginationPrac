package com.ssafy.paginationpractice.domain.board.dao;

import com.ssafy.paginationpractice.domain.board.entity.Board;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardDao extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b ")
    Page<Board> findBoardWithPaging(Pageable pageable);

    @Query("SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments WHERE b.id IN :boardIds")
    List<Board> findBoardWithCommentsByIds(@Param("boardIds") List<Long> boardIds);
}

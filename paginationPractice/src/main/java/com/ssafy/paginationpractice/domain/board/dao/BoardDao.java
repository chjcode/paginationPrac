package com.ssafy.paginationpractice.domain.board.dao;

import com.ssafy.paginationpractice.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDao extends JpaRepository<Board, Long> {

}

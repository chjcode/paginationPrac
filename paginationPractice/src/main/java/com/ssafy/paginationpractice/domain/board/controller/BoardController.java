package com.ssafy.paginationpractice.domain.board.controller;

import com.ssafy.paginationpractice.domain.board.dto.BoardSaveRequestDto;
import com.ssafy.paginationpractice.domain.board.dto.BoardSaveResponseDto;
import com.ssafy.paginationpractice.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping()
    public BoardSaveResponseDto save(
            @RequestBody BoardSaveRequestDto requestDto
    ){
        return boardService.save(requestDto);
    }

    @GetMapping()
    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "boardId") Long boardId
    ) {
        boardService.delete(boardId);
        return ResponseEntity.ok("댓글 삭제 완료(재귀)");
    }

    @DeleteMapping("/delete-batch/{boardId}")
    public ResponseEntity<?> deleteBatch(
            @PathVariable(name = "boardId") Long boardId
    ) {
        boardService.deleteBatch(boardId);
        return ResponseEntity.ok("댓글 삭제 완료(배치)");
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        boardService.findAll(pageable);
    }
}

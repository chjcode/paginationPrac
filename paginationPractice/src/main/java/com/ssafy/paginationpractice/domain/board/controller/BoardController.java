package com.ssafy.paginationpractice.domain.board.controller;

import com.ssafy.paginationpractice.domain.board.dto.*;
import com.ssafy.paginationpractice.domain.board.entity.Board;
import com.ssafy.paginationpractice.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        BoardPaginatedResponseDto<BoardPreviewDto> response = boardService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-keyset")
    public ResponseEntity<?> findAllByKeyset(
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Service 호출
        BoardKeysetResponseDto<BoardPreviewDto> boards = boardService.findAllByKeyset(lastId, size);

        return ResponseEntity.ok(boards);
    }
}

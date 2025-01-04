package com.ssafy.paginationpractice.domain.comment.controller;

import com.ssafy.paginationpractice.domain.comment.dto.CommentSaveRequestDto;
import com.ssafy.paginationpractice.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public ResponseEntity<?> save(
            @PathVariable(name = "boardId") Long boardId,
            @RequestBody CommentSaveRequestDto requestDTO
    ) {
        commentService.save(boardId, requestDTO);

        return ResponseEntity.ok().build();
    }
}

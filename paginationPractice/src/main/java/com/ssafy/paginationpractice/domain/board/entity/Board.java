package com.ssafy.paginationpractice.domain.board.entity;

import com.ssafy.paginationpractice.domain.base.BaseTimeEntity;
import com.ssafy.paginationpractice.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    protected Board(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}

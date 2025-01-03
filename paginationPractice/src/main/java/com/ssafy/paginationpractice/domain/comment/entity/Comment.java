package com.ssafy.paginationpractice.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.paginationpractice.domain.base.BaseTimeEntity;
import com.ssafy.paginationpractice.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Board board;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @JsonIgnore
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> childComments = new ArrayList<>();

    private boolean deleted = false;

    @Builder
    public Comment(Board board, String content, Comment parentComment) {
        this.board = board;
        this.content = content;
        this.parentComment = parentComment;

        if(parentComment != null) {
            parentComment.getChildComments().add(this);
        }
    }
}

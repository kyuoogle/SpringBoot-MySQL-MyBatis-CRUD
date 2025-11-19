package com.ykk.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private Long boardId;
    private String commentWriter;
    private String commentPass;
    private String commentContents;
    private int likeCount;
    private String createdAt;
}

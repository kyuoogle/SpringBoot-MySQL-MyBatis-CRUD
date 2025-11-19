package com.ykk.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString

public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private String createdAt;
    private int boardLike;

    private int CommentCount;
    private int totalCommentLike;

    // 사진 저장을 위한 것, 일반 CRUD는 없어도 됨
    private int fileAttached;
    private List<MultipartFile> boardFile;
}

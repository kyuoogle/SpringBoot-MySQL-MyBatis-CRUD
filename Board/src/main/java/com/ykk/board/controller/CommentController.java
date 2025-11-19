package com.ykk.board.controller;

import com.ykk.board.dto.CommentDTO;
import com.ykk.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/save")
    public String save(@ModelAttribute CommentDTO commentDTO) {
        commentService.save(commentDTO);
        return "redirect:/" + commentDTO.getBoardId();
    }

    // 댓글 수정 (비밀번호 검증 포함)
    @PostMapping("/update")
    public String update(@ModelAttribute CommentDTO commentDTO) {
        commentService.update(commentDTO);
        // 비밀번호 틀려도 일단 다시 상세 페이지로
        return "redirect:/" + commentDTO.getBoardId();
    }

    // 댓글 삭제 (비밀번호 검증 포함)
    @PostMapping("/delete")
    public String delete(@RequestParam Long id,
                         @RequestParam Long boardId,
                         @RequestParam String commentPass) {
        commentService.delete(id, commentPass);
        return "redirect:/" + boardId;
    }

    // 댓글 좋아요
    @PostMapping("/like")
    public String like(@RequestParam Long id,
                       @RequestParam Long boardId) {
        commentService.increaseLike(id);
        return "redirect:/" + boardId;
    }
}

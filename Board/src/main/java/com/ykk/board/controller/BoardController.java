package com.ykk.board.controller;

import com.ykk.board.dto.BoardFileDTO;
import com.ykk.board.dto.CommentDTO;
import com.ykk.board.service.CommentService;
import org.springframework.ui.Model;
import com.ykk.board.dto.BoardDTO;
import com.ykk.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String save() {
        return "save";
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO) throws IOException {
        // 파일 처리가 포함되므로 throws IOException
        boardService.save(boardDTO);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        // 1. 조회수 증가
        boardService.updateHits(id);

        // 2. 게시글 조회
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        // 3. 파일이 있다면 해당 게시글의 파일 목록도 조회
        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileDTOList", boardFileDTOList);
        }

        // 댓글 목록
        List<CommentDTO> comments = commentService.findByBoardId(id);
        model.addAttribute("comments", comments);

        return "detail";
    }

    // 수정 화면 (GET /update/{id})
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update"; // update.html
    }

    // 수정 처리 (POST /update/{id})
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute BoardDTO boardDTO,
                         Model model) {

        // 혹시나 해서 id를 확실하게 세팅
        boardDTO.setId(id);

        // 1) DB에 수정 반영
        boardService.update(boardDTO);

        // 2) 다시 상세 내용 조회
        BoardDTO dto = boardService.findById(id);
        model.addAttribute("board", dto);

        // 3) 수정된 상세 페이지로 이동
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }

    @PostMapping("/board/{id}/like")
    @ResponseBody
    public int like(@PathVariable Long id) {
        return boardService.increaseLike(id); // 증가 후 수 반환
    }

}

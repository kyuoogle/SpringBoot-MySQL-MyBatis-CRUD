package com.ykk.board.service;

import com.ykk.board.dto.CommentDTO;
import com.ykk.board.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;


    public void save(CommentDTO commentDTO) {
        commentMapper.save(commentDTO);
    }

    public List<CommentDTO> findByBoardId(Long boardId) {
        return commentMapper.findByBoardId(boardId);
    }

    public boolean update(CommentDTO commentDTO) {
        return commentMapper.updateWithPass(commentDTO) == 1;
    }

    public boolean delete(Long id, String commentPass) {
        return commentMapper.deleteWithPass(id, commentPass) == 1;
    }

    public void increaseLike(Long id) {
        commentMapper.increaseLike(id);
    }
}

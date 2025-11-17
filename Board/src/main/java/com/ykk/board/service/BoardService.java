package com.ykk.board.service;

import com.ykk.board.dto.BoardDTO;
import com.ykk.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;  // ← 여기만 바뀜

    public void save(BoardDTO boardDTO) {
        boardMapper.save(boardDTO);         // ← 여기
    }

    public List<BoardDTO> findAll() {
        return boardMapper.findAll();       // ← 여기
    }

    public void updateHits(Long id) {
        boardMapper.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardMapper.findById(id);
    }
}

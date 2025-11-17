package com.ykk.board.repository;

import com.ykk.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper  //  이게 있어야 MyBatis가 스캔해서 Bean 등록함
public interface BoardMapper {

    void save(BoardDTO board);

    List<BoardDTO> findAll();

    void updateHits(Long id);

    BoardDTO findById(Long id);
}
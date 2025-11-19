package com.ykk.board.repository;

import com.ykk.board.dto.BoardDTO;
import com.ykk.board.dto.BoardFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper  //  이게 있어야 MyBatis가 스캔해서 Bean 등록함
public interface BoardMapper {

    void save(BoardDTO board); // 게시글 저장

    List<BoardDTO> findAll(); //

    void updateHits(Long id); // 조회 수 증가

    BoardDTO findById(Long id); // 상세 조회

    void update(BoardDTO boardDTO);

    void delete(Long id);

    void increaseLike(Long id);

    Integer findLikeById(Long id);

    void saveFile(BoardFileDTO boardFileDTO);  // 파일 정보 저장
    List<BoardFileDTO> findFile(Long id);// 특정 게시글의 파일 목록


}
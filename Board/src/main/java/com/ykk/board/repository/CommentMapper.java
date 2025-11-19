package com.ykk.board.repository;

import com.ykk.board.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    void save(CommentDTO commentDTO);

    List<CommentDTO> findByBoardId(Long boardId);

    int updateWithPass(CommentDTO commentDTO);

    int deleteWithPass(@Param("id") Long id,
                       @Param("commentPass") String commentPass);

    void increaseLike(Long id);
}

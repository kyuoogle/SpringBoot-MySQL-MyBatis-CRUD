package com.ykk.board.service;

import com.ykk.board.dto.BoardDTO;
import com.ykk.board.dto.BoardFileDTO;
import com.ykk.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;  // ← 여기만 바뀜

    // WebConfig.savePath와 실제 폴더가 일치해야 한다.
    private final String savePath = "C:/upload/";


    public List<BoardDTO> findAll() {
        return boardMapper.findAll();
    }

    public void save(BoardDTO boardDTO) throws IOException {

        // 파일 입력이 아예 없으면 boardFile이 null일 수도 있으니 방어 코드 추가
        if (boardDTO.getBoardFile() == null || boardDTO.getBoardFile().get(0).isEmpty()) {
            // 파일 없음
            boardDTO.setFileAttached(0);
            boardMapper.save(boardDTO);   // 단순 게시글 저장

        } else {
            // 파일 존재
            boardDTO.setFileAttached(1);

            // 1. 먼저 board 테이블에 insert
            // useGeneratedKeys="true", keyProperty="id" 덕분에
            // insert 이후 boardDTO.getId()에 pk 값이 들어온다.
            boardMapper.save(boardDTO);

            // 2. 방금 저장된 게시글의 id
            Long boardId = boardDTO.getId();

            // 3. 파일 리스트 처리
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;

                // 파일 DTO 생성
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFileName);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(boardId);

                // 실제 파일 저장 경로
                String fullPath = savePath + storedFileName;

                // 실제 파일 저장
                boardFile.transferTo(new File(fullPath));

                // 파일 정보 DB 저장
                boardMapper.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardMapper.findFile(id);
    }

    public void updateHits(Long id) {
        boardMapper.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardMapper.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardMapper.update(boardDTO);
    }

    public void delete(Long id) {
        boardMapper.delete(id);
    }
}

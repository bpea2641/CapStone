package com.example.capstone.dto;

import com.example.capstone.entity.BoardEntity;
import com.example.capstone.entity.BoardFileEntity;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Transactional
@Builder
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    private MultipartFile boardFile; // save.html -> Controller 파일 담는 용도
    String originalFileName; // 원본 파일 이름
    String storedFileName; // 서버 저장용 파일 이름
    @Builder.Default
    private int fileAttached=0;
    // 파일 첨부 여부(첨부 1, 미첨부 0)


    public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());

        int fileAttached = boardEntity.getFileAttached();
        boardDTO.setFileAttached(fileAttached);

        if (fileAttached == 1) { // 파일이 첨부되어 있는 경우에만 파일 관련 정보 설정
            // 파일 이름을 가져와서 설정
            List<BoardFileEntity> boardFileEntityList = boardEntity.getBoardFileEntityList();
            if (!boardFileEntityList.isEmpty()) {
                BoardFileEntity boardFileEntity = boardFileEntityList.get(0); // 첫 번째 파일 정보 가져옴
                boardDTO.setOriginalFileName(boardFileEntity.getOriginalFileName());
                boardDTO.setStoredFileName(boardFileEntity.getStoredFileName());
            }
        }

        return boardDTO;
    }
}

package youresg.yesg.service;

import youresg.yesg.domain.board.Board;
import youresg.yesg.dto.board.*;

import java.util.List;
import java.util.Optional;

public interface IBoardService {

    List<BoardDto> findAllBoards();
    List<BoardDto> findAllBoardsBySearch(BoardSearchDto boardSearchDto);
    List<BoardDto> findAllBoardsByHashtagName(String tagName);

    Optional<BoardDto> findBoardById(Long boardId);
    BoardDto saveHashtag(Board board, String hashtag);
    BoardDto createBoard (BoardDto boardDto, Long memberId);
    BoardDto updateBoard(Long boardId, BoardDto updateBoardDto);
    void deleteBoard(Long boardId);
    void updateHashtag(Board board, BoardDto updatedBoardDto);


    }

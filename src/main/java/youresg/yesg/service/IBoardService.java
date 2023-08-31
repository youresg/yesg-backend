package youresg.yesg.service;

import youresg.yesg.domain.board.Board;
import youresg.yesg.dto.board.*;

import java.util.List;
import java.util.Optional;

public interface IBoardService {

    List<BoardDto> findAllBoards();
    List<BoardDto> findAllBoardsBySearch(BoardSearchDto boardSearchDto);
    List<BoardDto> findAllBoardsByHashtagId(String tagName);
    Optional<BoardDto> findBoardById(Long boardId);
    BoardDto createBoard(BoardDto boardDto, Long memberId);
    BoardDto updateBoard(Long boardId, BoardDto boardDto);
    void deleteBoard(Long boardId);

}

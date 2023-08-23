package youresg.yesg.service;

import youresg.yesg.dto.board.BoardDto;
import youresg.yesg.dto.board.BoardSearch;

import java.util.List;
import java.util.Optional;

public interface IBoardService {

    List<BoardDto> findAllBoards();
    List<BoardDto> findAllBoardsBySearch(BoardSearch boardSearch);
    List<BoardDto> findAllBoardsByHashtagId(String tagName);
    Optional<BoardDto> findBoardById(Long boardId);
    BoardDto createBoard(BoardDto boardDto);
    BoardDto updateBoard(Long recordId, String updatedBoard);
    void deleteBoard(Long boardId);

}

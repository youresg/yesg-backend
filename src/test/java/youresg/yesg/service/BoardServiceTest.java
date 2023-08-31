package youresg.yesg.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import youresg.yesg.component.DatabaseCleaner;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.board.BoardRepository;
import youresg.yesg.domain.board.Hashtag;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.dto.board.BoardDto;
import youresg.yesg.dto.board.BoardSearchDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
public class BoardServiceTest {

    @Autowired
    DatabaseCleaner databaseCleaner;
    @Autowired
    BoardRepository boardRepository;
    @Autowired IBoardService boardService;
    @Autowired MemberRepository memberRepository;

    private Board board1;
    private Board board2;

    @BeforeEach
    void setUp(){
        //given
        board1 = Board.builder()
                .title("테스트제목1")
                .content("테스트본문1")
                //.boardHashtagList(Hashtag.builder().tagName("테스트1").build().getBoardHashtagList())
                .build();
        board2 = Board.builder()
                .title("테스트제목2")
                .content("테스트본문2")
                //.boardHashtagList(Hashtag.builder().tagName("테스트2").build().getBoardHashtagList())
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);
    }
    @AfterEach
    void tearDown(){
        databaseCleaner.truncateAllEntity();
    }

    @Test
    void findAllBoards(){
        //when
        List<BoardDto> boardDtoList = boardService.findAllBoards();

        //then
        assertThat(boardDtoList.get(0).getTitle()).isEqualTo(board1.getTitle());
        assertThat(boardDtoList.get(0).getContent()).isEqualTo(board1.getContent());
       // assertThat(boardDtoList.get(0).getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0));

        assertThat(boardDtoList.get(1).getTitle()).isEqualTo(board2.getTitle());
        assertThat(boardDtoList.get(1).getContent()).isEqualTo(board2.getContent());
        //assertThat(boardDtoList.get(1).getHashtags().get(0)).isEqualTo(board2.getBoardHashtagList().get(0));

    }

    @Test
    void findAllBoardsBySearch(){
        //when
        BoardSearchDto boardSearchDto = BoardSearchDto.builder().keyword("테스트").build();
        List<BoardDto> boardDtoList = boardService.findAllBoardsBySearch(boardSearchDto);


        //then
        assertThat(boardDtoList.get(0).getTitle()).isEqualTo(board1.getTitle());
        assertThat(boardDtoList.get(0).getContent()).isEqualTo(board1.getContent());
        //assertThat(boardDtoList.get(0).getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0));

        assertThat(boardDtoList.get(1).getTitle()).isEqualTo(board2.getTitle());
        assertThat(boardDtoList.get(1).getContent()).isEqualTo(board2.getContent());
//        assertThat(boardDtoList.get(1).getHashtags().get(0)).isEqualTo(board2.getBoardHashtagList().get(0));

    }

    @Test
    void findAllBoardsByHashtagId(){
//        //when
//        List<BoardDto>boardDtoList1 = boardService.findAllBoardsByHashtagId("테스트1");
//        List<BoardDto>boardDtoList2 = boardService.findAllBoardsByHashtagId("테스트2");
//
//        //then
//        assertThat(boardDtoList1.get(0).getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0));
//        assertThat(boardDtoList2.get(0).getHashtags().get(0)).isEqualTo(board2.getBoardHashtagList().get(0));

    }


    @Test
    void findBoardById(){
        //when
        BoardDto boardDto = boardService.findBoardById(board1.getId()).get();

        //then
        assertThat(boardDto.getTitle()).isEqualTo(board1.getTitle());
        assertThat(boardDto.getContent()).isEqualTo(board1.getContent());
        //assertThat(boardDto.getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0));
    }
    @Test
    void createBoard(){
        //given
        //when
        //BoardDto boardDto = boardService.createBoard();

        //then
    }
    @Test
    void updateBoard(){
        //given
        BoardDto boardDto = BoardDto.builder()
                .content("Content Update")
                .build();
        //when
        BoardDto updateBoardDto = boardService.updateBoard(board1.getId(), boardDto);

        //then
        assertThat(updateBoardDto.getTitle()).isEqualTo(board1.getTitle());
        assertThat(updateBoardDto.getContent()).isNotEqualTo(board1.getContent());
      //  assertThat(updateBoardDto.getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0));

    }
    @Test
    void deleteBoard(){
        //when
        boardService.deleteBoard(board1.getId());
        Optional<BoardDto> boardDetail = boardService.findBoardById(board1.getId());

        //then
        assertFalse(boardDetail.isPresent());
    }
}

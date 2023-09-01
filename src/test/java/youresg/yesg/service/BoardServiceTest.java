package youresg.yesg.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import youresg.yesg.component.DatabaseCleaner;
import youresg.yesg.domain.board.*;
import youresg.yesg.domain.member.Member;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.domain.member.SocialProvider;
import youresg.yesg.dto.board.BoardDto;
import youresg.yesg.dto.board.BoardSearchDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
public class BoardServiceTest {

    @Autowired
    DatabaseCleaner databaseCleaner;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    IBoardService boardService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired
    BoardHashtagRepository boardHashtagRepository;


    private Board board1;
    private Board board2;
    private Hashtag hashtag1;
    private Hashtag hashtag2;


    @BeforeEach
    void setUp() {
        //given
        hashtag1 = Hashtag.builder().tagName("테스트1").build();
        hashtag2 = Hashtag.builder().tagName("테스트2").build();
        hashtagRepository.save(hashtag1);
        hashtagRepository.save(hashtag2);


        board1 = Board.builder()
                .title("테스트제목1")
                .content("테스트본문1")
                .build();
        board2 = Board.builder()
                .title("테스트제목2")
                .content("테스트본문2")
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);

        BoardDto boardDto1 = boardService.saveHashtag(board1, hashtag1.getTagName());
        BoardDto boardDto2 = boardService.saveHashtag(board2, hashtag2.getTagName());
        System.out.println("board1.getBoardHashtagList().get(0).getHashtag().getTagName() = " + board1.getBoardHashtagList().get(0).getHashtag().getTagName());

    }

    @AfterEach
    void tearDown() {
        databaseCleaner.truncateAllEntity();
    }

    @Test
    void findAllBoards() {
        //when
        List<BoardDto> boardDtoList = boardService.findAllBoards();

        //then
        assertThat(boardDtoList.get(0).getTitle()).isEqualTo(board1.getTitle());
        assertThat(boardDtoList.get(0).getContent()).isEqualTo(board1.getContent());
        assertThat(boardDtoList.get(0).getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0).getHashtag().getTagName());

        assertThat(boardDtoList.get(1).getTitle()).isEqualTo(board2.getTitle());
        assertThat(boardDtoList.get(1).getContent()).isEqualTo(board2.getContent());
        assertThat(boardDtoList.get(1).getHashtags().get(0)).isEqualTo(board2.getBoardHashtagList().get(0).getHashtag().getTagName());

    }

    @Test
    void findAllBoardsBySearcrh() {
        //when
        BoardSearchDto boardSearchDto = BoardSearchDto.builder().keyword("테스트").build();
        List<BoardDto> boardDtoList = boardService.findAllBoardsBySearch(boardSearchDto);


        //then
        assertThat(boardDtoList.get(0).getTitle()).isEqualTo(board1.getTitle());
        assertThat(boardDtoList.get(0).getContent()).isEqualTo(board1.getContent());
        assertThat(boardDtoList.get(0).getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0).getHashtag().getTagName());

        assertThat(boardDtoList.get(1).getTitle()).isEqualTo(board2.getTitle());
        assertThat(boardDtoList.get(1).getContent()).isEqualTo(board2.getContent());
        assertThat(boardDtoList.get(1).getHashtags().get(0)).isEqualTo(board2.getBoardHashtagList().get(0).getHashtag().getTagName());

    }

    @Test
    void findAllBoardsByHashtagName() {
        //when
        List<BoardDto> boardDtoList1 = boardService.findAllBoardsByHashtagName("테스트1");
        List<BoardDto> boardDtoList2 = boardService.findAllBoardsByHashtagName("test");

        //then
        assertThat(boardDtoList1.size()).isEqualTo(1);
        assertThat(boardDtoList2.size()).isEqualTo(0);

    }


    @Test
    void findBoardById() {
        //when
        BoardDto boardDto = boardService.findBoardById(board1.getId()).get();

        //then
        assertThat(boardDto.getTitle()).isEqualTo(board1.getTitle());
        assertThat(boardDto.getContent()).isEqualTo(board1.getContent());
        assertThat(boardDto.getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0).getHashtag().getTagName());
    }

    @Test
    void saveHashtag() {
        //given
        String newTag = "newTag!";

        //when
        BoardDto boardDto = boardService.saveHashtag(board1, newTag);

        //then
        assertThat(boardDto.getHashtags().get(1)).isEqualTo(board1.getBoardHashtagList().get(1).getHashtag().getTagName());
    }

    @Test
    void createBoard() {
        //given
        Member member = Member.builder().username("member1")
                .email("test1@gmail.com")
                .socialProvider(SocialProvider.GOOGLE)
                .bio("Hello world !")
                .company("Sejong Univ.")
                .location("Seoul, Republic of Korea")
                .build();
        memberRepository.save(member);

        String title = "테스트 제목1";
        String content = "테스트 본문1";
        String tagName = "tag";

        BoardDto boardDto = BoardDto.builder()
                .title(title)
                .content(content)
                .build();
        boardDto = boardService.saveHashtag(boardDto.toEntity(), tagName);
        //when
        BoardDto createBoardDto = boardService.createBoard(boardDto, member.getId());

        //then
        assertThat(createBoardDto.getTitle()).isEqualTo(title);
        assertThat(createBoardDto.getContent()).isEqualTo(content);
        assertThat(createBoardDto.getHashtags().get(0)).isEqualTo(tagName);

    }

    @Test
    void updateBoard() {
        //given
        BoardDto boardDto = BoardDto.builder()
                .title(BoardDto.toDto(board1).getTitle())
                .content("Content Update")
                .hashtags(BoardDto.toDto(board1).getHashtags())
                .build();
        //when
        //System.out.println("board1.getBoardHashtagList().get(0).getHashtag().getTagName() = " + board1.getBoardHashtagList().get(0).getHashtag().getTagName());
        BoardDto updateBoardDto = boardService.updateBoard(board1.getId(), boardDto);
        //then
        assertThat(updateBoardDto.getTitle()).isEqualTo(board1.getTitle());
        assertThat(updateBoardDto.getContent()).isNotEqualTo(board1.getContent());
        assertThat(updateBoardDto.getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0).getHashtag().getTagName());

    }

    @Test
    void deleteBoard() {
        //when
        boardService.deleteBoard(board1.getId());
        Optional<BoardDto> boardDetail = boardService.findBoardById(board1.getId());

        //then
        assertFalse(boardDetail.isPresent());
    }

    @Test
    void updateHashtag() {
        //given
        String newTag1 = "newTag!!";
        String newTag2 = "newTag222";
        List<String> newTags = new ArrayList<>();
        newTags.add(newTag1);
        newTags.add(newTag2);
        
        BoardDto newBoardHashtag = BoardDto.builder()
                .title(BoardDto.toDto(board1).getTitle())
                .content(BoardDto.toDto(board1).getContent())
                .hashtags(newTags)
                .build();
        
        //when
        boardService.updateHashtag(board1, newBoardHashtag);

        //then
        System.out.println("newBoardHashtag.getHashtags() = " + newBoardHashtag.getHashtags());
        assertThat(newBoardHashtag.getHashtags().get(0)).isEqualTo(board1.getBoardHashtagList().get(0).getHashtag().getTagName());

    }
}
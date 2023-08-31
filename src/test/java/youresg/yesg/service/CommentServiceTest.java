package youresg.yesg.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import youresg.yesg.component.DatabaseCleaner;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.comment.Comment;
import youresg.yesg.domain.comment.CommentRepository;
import youresg.yesg.domain.member.Member;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.domain.member.SocialProvider;
import youresg.yesg.dto.board.BoardDto;
import youresg.yesg.dto.comment.CommentDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    DatabaseCleaner databaseCleaner;

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ICommentService commentService;
    @Autowired
    MemberRepository memberRepository;


    private Comment comment1;
    private Comment comment2;

    @BeforeEach
    void setUp(){
        //given
        comment1 = Comment.builder()
                .content("테스트댓글1")
                .build();
        comment2 = Comment.builder()
                .content("테스트댓글2")
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);
    }
    @AfterEach
    void tearDown(){
        databaseCleaner.truncateAllEntity();
    }

    @Test
    void createComment(){
        //given
        Member member = Member.builder().username("member1")
                .email("test1@gmail.com")
                .socialProvider(SocialProvider.GOOGLE)
                .bio("Hello world !")
                .company("Sejong Univ.")
                .location("Seoul, Republic of Korea")
                .build();
        memberRepository.save(member);

        String content  = "테스트 본문1";
        CommentDto commentDto = CommentDto.builder()
                .content("테스트 본문1")
                .build();

        //when
        CommentDto createCommentDto = commentService.createComment(commentDto, member.getId());
        //then
        assertThat(createCommentDto.getContent()).isEqualTo(content);
    }

    @Test
    void updateComment(){
        //given
        CommentDto commentDto = CommentDto.builder()
                .content("Content Update")
                .build();
        //when
        CommentDto updateCommentDto = commentService.updateComment(comment1.getId(), commentDto);

        //then
        assertThat(updateCommentDto.getContent()).isNotEqualTo(comment1.getContent());

    }

    @Test
    void deleteComment(){
        //when
        commentService.deleteComment(comment1.getId());
        commentService.deleteComment(comment2.getId());

        //then
        assertEquals(0, commentRepository.count());
    }
}

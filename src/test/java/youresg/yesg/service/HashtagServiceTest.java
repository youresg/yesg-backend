package youresg.yesg.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import youresg.yesg.component.DatabaseCleaner;
import youresg.yesg.domain.board.Hashtag;
import youresg.yesg.domain.board.HashtagRepository;
import youresg.yesg.domain.comment.Comment;
import youresg.yesg.dto.board.BoardDto;
import youresg.yesg.dto.board.HashtagDto;

import java.util.List;

@SpringBootTest
public class HashtagServiceTest {

    @Autowired
    DatabaseCleaner databaseCleaner;
    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired
    IHashtagService hashtagService;

    private Hashtag hashtag1;
    private Hashtag hashtag2;

    @BeforeEach
    void setUp(){
        //given
        hashtag1 = Hashtag.builder()
                .tagName("Tag1")
                .build();
        hashtag2 = Hashtag.builder()
                .tagName("Tag2")
                .build();

        hashtagRepository.save(hashtag1);
        hashtagRepository.save(hashtag2);
    }
    @AfterEach
    void tearDown(){
        databaseCleaner.truncateAllEntity();
    }

    @Test
    void findAllBoardsByHashtagId(){

    }

    @Test
    void createHashtag(){

    }

    @Test
    void deleteHashtag(){

    }


}

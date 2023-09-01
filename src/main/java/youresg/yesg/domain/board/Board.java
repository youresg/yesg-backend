package youresg.yesg.domain.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youresg.yesg.component.auditing.BaseEntity;
import youresg.yesg.domain.comment.Comment;
import youresg.yesg.domain.member.Member;
import youresg.yesg.dto.board.BoardDto;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;


@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    private int viewCount;

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = ALL, orphanRemoval = true)
    private List<Heart> heartList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = ALL, orphanRemoval = true)
    private List<BoardHashtag> boardHashtagList = new ArrayList<>();


    public void update(BoardDto updatedBoardDto){
        if (updatedBoardDto.getTitle() != null) this.title = updatedBoardDto.getTitle();
        if (updatedBoardDto.getContent() != null) this.content = updatedBoardDto.getContent();
        //if (updatedBoardDto.getHashtags() != null) this.boardHashtagList = updatedBoardDto.getBoardHashtags();

    }
    public void addEntity(BoardHashtag boardHashtags){

        this.boardHashtagList.add(boardHashtags);
    }
}

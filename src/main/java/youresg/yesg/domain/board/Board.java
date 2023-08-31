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

//    public static Board createBoard(String title, String content, Member member, List<BoardHashtag> hashtags) {
//        return Board.builder()
//                .title(title).content(content).member(member).boardHashtagList(hashtags)
//                .build();
//    }

    public void update(BoardDto updateDto){
        if (updateDto.getTitle() != null) this.title = updateDto.getTitle();
        if (updateDto.getContent() != null) this.content = updateDto.getContent();
        if (updateDto.getHashtags() != null) this.boardHashtagList = updateDto.getHashtags();
    }
}

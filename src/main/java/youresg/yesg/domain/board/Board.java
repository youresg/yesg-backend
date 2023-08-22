package youresg.yesg.domain.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youresg.yesg.component.auditing.BaseEntity;
import youresg.yesg.domain.comment.Comment;
import youresg.yesg.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

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

    private int view_count;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "board")
    private List<Heart> heartList;

    @OneToMany(mappedBy = "board")
    private List<BoardHashtag> boardHashtagList = new ArrayList<>();

}

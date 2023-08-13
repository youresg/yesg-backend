package youresg.yesg.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youresg.yesg.board.domain.Board;
import youresg.yesg.board.domain.Comment;
import youresg.yesg.record.domain.Record;
import youresg.yesg.score.domain.Score;

import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    private String profileImg;

    @Enumerated
    private Role role;

    @Enumerated
    private SocialProvider socialProvider;

    @OneToMany(mappedBy = "member")
    private List<Score> scoreList;

    @OneToMany(mappedBy = "member")
    private List<Record> recordList;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList;

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList;

}

package youresg.yesg.domain.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youresg.yesg.component.auditing.BaseEntity;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.board.Heart;
import youresg.yesg.domain.comment.Comment;
import youresg.yesg.domain.record.Record;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    private String profileImg;

    private String bio;

    private String company;

    private String location;

    private Boolean isPublic;

    @Enumerated(STRING)
    private Role role;

    @Enumerated(STRING)
    private SocialProvider socialProvider;

    @Embedded
    private Score score;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Record> recordList;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Board> boardList;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Heart> heartList;

}

package youresg.yesg.domain.member;

import jakarta.persistence.*;
import lombok.*;
import youresg.yesg.component.auditing.BaseEntity;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.board.Heart;
import youresg.yesg.domain.comment.Comment;
import youresg.yesg.domain.record.Record;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@ToString(of = {"id", "username", "email", "profileImg", "bio", "company", "location", "isPublic", "role", "socialProvider"})
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

    @Builder.Default
    private Boolean isPublic = Boolean.TRUE;

    @Builder.Default
    @Enumerated(STRING)
    private Role role = Role.GUEST;

    @Enumerated(STRING)
    private SocialProvider socialProvider;

    @Builder.Default
    @Embedded
    private Score score = new Score(0, 0, 0, 0, Grade.WHITE);

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Record> recordList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Heart> heartList = new ArrayList<>();

    public Member updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
        return this;
    }
    public void updateRoleKey(Role role) {
        this.role = role;
    }

}

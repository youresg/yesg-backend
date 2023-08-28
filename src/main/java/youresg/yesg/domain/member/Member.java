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
import youresg.yesg.dto.member.MemberDto;

import java.util.ArrayList;
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

    public void updateMemberInfo(MemberDto updatedMemberDto) {
        if (updatedMemberDto.getUsername() != null) this.username = updatedMemberDto.getUsername();
        if (updatedMemberDto.getProfileImg() != null) this.profileImg = updatedMemberDto.getProfileImg();
        if (updatedMemberDto.getBio() != null) this.bio = updatedMemberDto.getBio();
        if (updatedMemberDto.getCompany() != null) this.company = updatedMemberDto.getCompany();
        if (updatedMemberDto.getLocation() != null) this.location = updatedMemberDto.getLocation();
        if (updatedMemberDto.getIsPublic() != null) this.isPublic = updatedMemberDto.getIsPublic();
    }

}

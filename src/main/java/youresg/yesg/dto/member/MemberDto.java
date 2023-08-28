package youresg.yesg.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import youresg.yesg.domain.member.Member;

@Getter
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String email;
    private String username;
    private String profileImg;
    private String bio;
    private String company;
    private String location;
    private Boolean isPublic;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .email(email)
                .username(username)
                .profileImg(profileImg)
                .bio(bio)
                .company(company)
                .location(location)
                .isPublic(isPublic).build();
    }

    public static MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .username(member.getUsername())
                .profileImg(member.getProfileImg())
                .bio(member.getBio())
                .company(member.getCompany())
                .location(member.getLocation())
                .isPublic(member.getIsPublic()).build();
    }

}

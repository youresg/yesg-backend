package youresg.yesg.service;

import jakarta.servlet.http.HttpServletRequest;
import youresg.yesg.dto.member.MemberDto;

import java.util.Optional;

public interface IMemberService {
    void logout(HttpServletRequest request);
    boolean isUsernameValid(String username);
    Optional<MemberDto> findMemberById(Long memberId);
    Optional<MemberDto> findByUsername(String username);
    MemberDto updateMember(Long memberId, MemberDto updatedMemberDto);

}

package youresg.yesg.service;

import youresg.yesg.dto.member.MemberDto;

import java.util.Optional;

public interface IMemberService {

    Optional<MemberDto> findMemberById(Long memberId);
    Optional<MemberDto> findByUsername(String username);
    MemberDto updateMember(Long memberId, MemberDto updatedMemberDto);

}

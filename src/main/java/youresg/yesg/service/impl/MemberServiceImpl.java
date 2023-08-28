package youresg.yesg.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import youresg.yesg.domain.member.Member;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.dto.member.MemberDto;
import youresg.yesg.service.IMemberService;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements IMemberService {

    private final MemberRepository memberRepository;

    @Override
    public void logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 토큰 무효화
        }
    }

    @Override
    public boolean isUsernameValid(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.isEmpty();
    }

    @Override
    public Optional<MemberDto> findMemberById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.map(MemberDto::toDto);
    }

    @Override
    public Optional<MemberDto> findByUsername(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.map(MemberDto::toDto);
    }

    @Override
    public MemberDto updateMember(Long memberId, MemberDto updatedMemberDto) {
        Member member = memberRepository.findById(memberId).get();  // TODO 예외 처리
        member.updateMemberInfo(updatedMemberDto);
        return MemberDto.toDto(member);
    }

}

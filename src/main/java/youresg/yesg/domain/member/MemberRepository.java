package youresg.yesg.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import youresg.yesg.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}

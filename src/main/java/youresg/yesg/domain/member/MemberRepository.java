package youresg.yesg.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import youresg.yesg.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

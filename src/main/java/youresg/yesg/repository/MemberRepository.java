package youresg.yesg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import youresg.yesg.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

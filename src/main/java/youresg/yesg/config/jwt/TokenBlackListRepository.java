package youresg.yesg.config.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlackListRepository extends JpaRepository<TokenBlacklist, String> {
}

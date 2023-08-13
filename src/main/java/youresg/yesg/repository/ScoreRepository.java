package youresg.yesg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import youresg.yesg.domain.score.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}

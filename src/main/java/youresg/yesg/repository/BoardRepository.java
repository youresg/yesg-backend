package youresg.yesg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import youresg.yesg.domain.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}

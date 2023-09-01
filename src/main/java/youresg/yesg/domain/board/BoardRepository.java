package youresg.yesg.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query("SELECT b FROM Board b where b.title LIKE %:keyword%")
    List<Board> findBySearch(String keyword);

    @Query("SELECT DISTINCT b FROM Board b JOIN b.boardHashtagList h WHERE h.hashtag = :tagName")
    List<Board> findByName(String tagName);
}

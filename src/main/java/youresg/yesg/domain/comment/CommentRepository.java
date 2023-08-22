package youresg.yesg.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import youresg.yesg.domain.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

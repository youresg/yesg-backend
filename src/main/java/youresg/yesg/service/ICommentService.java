package youresg.yesg.service;

import org.springframework.transaction.annotation.Transactional;
import youresg.yesg.dto.comment.CommentDto;

public interface ICommentService {


    CommentDto createComment(CommentDto commentDto, Long memberId);

    CommentDto updateComment(Long commentId, CommentDto updatedComment);
    void deleteComment(Long commentId);

}

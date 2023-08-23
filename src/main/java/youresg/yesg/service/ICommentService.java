package youresg.yesg.service;

import youresg.yesg.dto.comment.CommentDto;

public interface ICommentService {

    CommentDto createComment(CommentDto commentDto);
    CommentDto updateComment(Long commentId, CommentDto updatedComment);
    void deleteComment(Long commentId);

}

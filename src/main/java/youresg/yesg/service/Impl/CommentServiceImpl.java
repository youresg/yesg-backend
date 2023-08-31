package youresg.yesg.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youresg.yesg.domain.comment.Comment;
import youresg.yesg.domain.comment.CommentRepository;
import youresg.yesg.domain.member.Member;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.dto.comment.CommentDto;
import youresg.yesg.service.ICommentService;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public CommentDto createComment(CommentDto commentDto, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        commentDto.setMember(member);
        Comment comment = commentDto.toEntity();
        Comment savedComment = commentRepository.save(comment);
        return CommentDto.toDto(savedComment);
    }

    @Transactional
    @Override
    public CommentDto updateComment(Long commentId, CommentDto updatedComment){
        Comment comment = commentRepository.findById(commentId).get();
        comment.update(updatedComment);
        return CommentDto.toDto(comment);
    }
    @Transactional
    @Override
    public void deleteComment(Long commentId){
        Comment comments = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id="+commentId));
        commentRepository.delete(comments);
    }
}

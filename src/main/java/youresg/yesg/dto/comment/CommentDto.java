package youresg.yesg.dto.comment;

import lombok.*;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.comment.Comment;
import youresg.yesg.domain.member.Member;
import youresg.yesg.dto.board.BoardDto;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long id;
    private Board board;
    private Member member;
    private String content;

    public Comment toEntity(){
        Comment comment = Comment.builder()
                .id(id)
                .member(member)
                .content(content)
                .board(board)
                .build();
        return comment;
    }

    @Builder
    public static CommentDto toDto (Comment entity){
        return CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .build();
    }

}

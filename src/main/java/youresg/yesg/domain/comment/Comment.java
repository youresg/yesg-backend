package youresg.yesg.domain.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youresg.yesg.component.auditing.BaseEntity;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.member.Member;
import youresg.yesg.dto.comment.CommentDto;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    public static Comment createComment(String content, Member member) {
        return Comment.builder()
                .content(content).member(member)
                .build();
    }

    public void update(CommentDto updatedComment) {
        if (updatedComment.getContent() != null) this.content = updatedComment.getContent();
    }
}
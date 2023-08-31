package youresg.yesg.dto.board;


import lombok.*;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.board.BoardHashtag;
import youresg.yesg.domain.member.Member;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

    private Long id;
    private Member member;
    private String title;
    private String content;
    private List<BoardHashtag> hashtags;
    private int viewCount;


    public Board toEntity(){
        Board board = Board.builder()
                .id(id)
                .member(member)
                .title(title)
                .content(content)
                .boardHashtagList(hashtags)
                .viewCount(viewCount)
                .build();
        return board;
    }

    @Builder
    public static BoardDto toDto (Board entity){
        return BoardDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .hashtags(entity.getBoardHashtagList())
                .build();
    }
}

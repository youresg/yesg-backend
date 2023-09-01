package youresg.yesg.dto.board;


import lombok.*;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.board.BoardHashtag;
import youresg.yesg.domain.board.Hashtag;
import youresg.yesg.domain.board.HashtagRepository;
import youresg.yesg.domain.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
    private int viewCount;
    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    public Board toEntity(){
        Board board = Board.builder()
                .id(id)
                .member(member)
                .title(title)
                .content(content)
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
                .hashtags(entity.getBoardHashtagList().stream().map(h-> h.getHashtag().getTagName()).collect(Collectors.toList()))
                .build();
    }
}

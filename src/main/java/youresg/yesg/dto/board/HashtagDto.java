package youresg.yesg.dto.board;

import lombok.*;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.board.Hashtag;
import youresg.yesg.domain.member.Member;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HashtagDto {

    private Long id;
    private String tagName ;

    public Hashtag toEntity() {
        Hashtag hashtag = Hashtag.builder()
                .id(id)
                .tagName(tagName)
                .build();
        return hashtag;
    }

    public static HashtagDto toDto(Hashtag entity) {
        return HashtagDto.builder()
                .id(entity.getId())
                .tagName(entity.getTagName())
                .build();
    }
}

package youresg.yesg.dto.board;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class BoardSearchDto {
    private String keyword;

    @Builder
    public BoardSearchDto(String keyword){
        this.keyword = keyword;

    }
}

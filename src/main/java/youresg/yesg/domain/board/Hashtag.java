package youresg.yesg.domain.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youresg.yesg.component.auditing.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Hashtag extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    @Column(nullable = false)
    private String tagName;

    @Builder.Default
    @OneToMany(mappedBy = "hashtag", cascade = ALL, orphanRemoval = true)
    private List<BoardHashtag> boardHashtagList = new ArrayList<>();

    public void addEntity(BoardHashtag boardHashtags){
        this.boardHashtagList.add(boardHashtags);
    }
}
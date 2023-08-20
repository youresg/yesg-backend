package youresg.yesg.domain.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Embeddable
public class Score {

    private int e;
    private int s;
    private int g;
    private int total;

    @Enumerated(STRING)
    private Grade grade;

}

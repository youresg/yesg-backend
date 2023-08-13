package youresg.yesg.record.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Activity {

    @Id
    @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    private String title;

    private String description;

    private int score;

    @Enumerated
    private EsgType esgType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

}

package youresg.yesg.domain.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import youresg.yesg.component.auditing.BaseEntity;
import youresg.yesg.domain.record.EsgType;
import youresg.yesg.domain.record.Record;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    private String name;

    private int score;

    @Enumerated
    private EsgType esgType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

}

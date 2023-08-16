package youresg.yesg.domain.record;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EsgType {
    ENVIRONMENT("TYPE_ENVIRONMENT", "환경", 1),
    SOCIAL("TYPE_SOCIAL", "사회", 3),
    GOVERNANCE("TYPE_GOVERNANCE", "지배구조", 5);

    private final String typeCode;
    private final String description;
    private final int score;
}
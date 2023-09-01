package youresg.yesg.config.jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class TokenBlacklist {

    @Id
    private String token;

}
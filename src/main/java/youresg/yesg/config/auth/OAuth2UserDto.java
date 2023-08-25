package youresg.yesg.config.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OAuth2UserDto {
    private String email;
    private String name;
    private String picture;

    public static OAuth2UserDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return OAuth2UserDto.builder()
                .email((String)attributes.get("email"))
                .name((String)attributes.get("name"))
                .picture((String)attributes.get("picture"))
                .build();
    }
}
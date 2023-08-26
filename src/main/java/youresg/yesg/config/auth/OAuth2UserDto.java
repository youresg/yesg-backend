package youresg.yesg.config.auth;

import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import youresg.yesg.domain.member.SocialProvider;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OAuth2UserDto {
    private String email;
    private String name;
    private String picture;
    private SocialProvider socialProvider;

    public static OAuth2UserDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return OAuth2UserDto.builder()
                .email((String)attributes.get("email"))
                .name((String)attributes.get("name"))
                .picture((String)attributes.get("picture"))
                .build();
    }
}
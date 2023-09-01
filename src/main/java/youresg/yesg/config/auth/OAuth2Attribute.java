package youresg.yesg.config.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import youresg.yesg.domain.member.*;

import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@ToString
@Builder(access = PRIVATE)
@Getter
class OAuth2Attribute {

    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String username;
    private String profileImg;

    static OAuth2Attribute of(String provider, String attributeKey, Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(attributeKey, attributes);
            case "kakao":
                return ofKakao("email", attributes);
            case "naver":
                return ofNaver("id", attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofGoogle(String attributeKey, Map<String, Object> attributes) {
        Map<String, Object> oauthAttributes = new HashMap<>(attributes);
        oauthAttributes.put("socialProvider", SocialProvider.GOOGLE);

        return OAuth2Attribute.builder()
                .attributes(oauthAttributes)
                .attributeKey(attributeKey)
                .email((String) attributes.get("email"))
                .username((String) attributes.get("name"))
                .profileImg((String) attributes.get("picture"))
                .build();
    }

    private static OAuth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes) {
//        Map<String, Object> oauthAttributes = new HashMap<>(attributes); TODO
        attributes.put("socialProvider", SocialProvider.KAKAO);

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .email((String) kakaoAccount.get("email"))
                .username((String) kakaoProfile.get("nickname"))
                .profileImg((String) kakaoProfile.get("profile_image_url"))
                .build();
    }

    private static OAuth2Attribute ofNaver(String attributeKey, Map<String, Object> attributes) {
        //        Map<String, Object> oauthAttributes = new HashMap<>(attributes); TODO
        attributes.put("socialProvider", SocialProvider.NAVER);

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .attributes(response)
                .attributeKey(attributeKey)
                .email((String) response.get("email"))
                .username((String) response.get("name"))
                .profileImg((String) response.get("profile_image"))
                .build();
    }

    public Member toEntity() {
        log.info("socialProvider = {}", SocialProvider.valueOf(attributes.get("socialProvider").toString()));
        return Member.builder()
                .username(username)
                .email(email)
                .profileImg(profileImg)
                .socialProvider(SocialProvider.GOOGLE)
                .build();
    }
}
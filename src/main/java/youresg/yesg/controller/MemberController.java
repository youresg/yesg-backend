package youresg.yesg.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youresg.yesg.config.jwt.TokenBlackListRepository;
import youresg.yesg.config.jwt.TokenBlacklist;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final TokenBlackListRepository tokenBlackListRepository;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        TokenBlacklist blacklist = new TokenBlacklist(token);
        tokenBlackListRepository.save(blacklist);
        return ResponseEntity.ok("Logged out successfully.");
    }

}

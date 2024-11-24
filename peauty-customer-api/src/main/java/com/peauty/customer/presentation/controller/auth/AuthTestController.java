package com.peauty.customer.presentation.controller.auth;

import com.peauty.customer.business.auth.AuthService;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.presentation.controller.auth.dto.SignUpResponse;
import com.peauty.domain.user.SocialPlatform;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Auth Test", description = "Auth Test API")
@RequestMapping("/v1/auth/test")
@RequiredArgsConstructor
public class AuthTestController {

    private final AuthService authService;

    @PostMapping("/sign")
    @Operation(summary = "고객 테스터 로그인&회원가입", description = "고객 테스터의 로그인&회원가입 통합 진입점입니다.")
    public SignUpResponse signTester(
            @RequestParam SocialPlatform socialPlatform,
            @RequestParam String idToken,
            @RequestParam String nickname,
            @RequestParam String phoneNum
    ) {
        SignUpResult result = authService.signWithIdToken(socialPlatform, idToken, nickname, phoneNum);
        return SignUpResponse.from(result);
    }

    @GetMapping("/sign-in")
    @Hidden
    public void testSignIn(
            @RequestParam("socialId") String socialId,
            @RequestParam("socialPlatform") SocialPlatform socialPlatform,
            @RequestParam("nickname") String nickname,
            @RequestParam("profileImageUrl") String profileImageUrl
    ) {
        log.info("""
                        Redirect Parameters:
                        socialId: {}
                        socialPlatform: {}
                        nickname: {}
                        profileImageUrl: {}
                        """,
                socialId, socialPlatform, nickname, profileImageUrl
        );
    }
}

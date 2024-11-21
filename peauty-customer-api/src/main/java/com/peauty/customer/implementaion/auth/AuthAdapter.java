package com.peauty.customer.implementaion.auth;

import com.peauty.customer.business.auth.AuthPort;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.User;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.SocialInfo;
import com.peauty.auth.facade.OidcProviderFacade;
import com.peauty.auth.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthPort {

    private final JwtProvider jwtProvider;
    private final OidcProviderFacade oidcProviderFacade;

    @Override
    public String getIdTokenByCode(SocialPlatform socialPlatform, String code) {
        return oidcProviderFacade.getIdToken(socialPlatform, code);
    }

    @Override
    public SocialInfo getSocialInfoFromIdToken(SocialPlatform socialPlatform, String idToken) {
        return oidcProviderFacade.getSocialInfo(socialPlatform, idToken);
    }

    @Override
    public SignTokens generateSignTokens(User user) {
        return jwtProvider.generateToken(user);
    }
}

package com.loladebadmus.simplecrudapp.security.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class GoogleOauthUserDTO implements OAuth2User {

    private OAuth2User oAuth2User;

    public GoogleOauthUserDTO(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public String getFirstName() {
        return oAuth2User.getAttribute("given_name");
    }

    public String getLastName() {
        return oAuth2User.getAttribute("family_name");
    }
}

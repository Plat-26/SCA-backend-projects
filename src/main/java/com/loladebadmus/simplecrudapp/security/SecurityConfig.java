package com.loladebadmus.simplecrudapp.security;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.loladebadmus.simplecrudapp.errors.AuthFailureHandler;
import com.loladebadmus.simplecrudapp.security.jwt.JwtTokenVerifier;
import com.loladebadmus.simplecrudapp.security.jwt.JwtUsernameAndPasswordFilter;
import com.loladebadmus.simplecrudapp.security.oauth.CustomOAuth2UserService;
import com.loladebadmus.simplecrudapp.security.oauth.GoogleOauthUserDTO;
import com.loladebadmus.simplecrudapp.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final NetHttpTransport transport;
    private final JsonFactory jsonFactory;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, CustomOAuth2UserService oAuth2UserService, NetHttpTransport transport, JsonFactory jsonFactory) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.oAuth2UserService = oAuth2UserService;
        this.transport = transport;
        this.jsonFactory = jsonFactory;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(transport, jsonFactory), JwtUsernameAndPasswordFilter.class)
                .authorizeRequests()
                .antMatchers("/users/register/**", "/logout")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .exceptionHandling().authenticationEntryPoint(new AuthFailureHandler());

        http.oauth2Login()
                .userInfoEndpoint()
                    .userService(oAuth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

                        OAuth2User oauthUser =  oauth2AuthenticationToken.getPrincipal();
                        GoogleOauthUserDTO googleOauthUserDTO = new GoogleOauthUserDTO(oauthUser);
                        userService.processOAuthPostLogin(googleOauthUserDTO);

                        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
                        String tokenValue = principal.getIdToken().getTokenValue();

                        response.addHeader(SecurityConstants.AUTHORIZATION_HEADER, tokenValue);
                        response.getOutputStream().print(tokenValue); //todo: make token available to client
                        response.sendRedirect("/users/register/oauth-complete");
                    }
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}

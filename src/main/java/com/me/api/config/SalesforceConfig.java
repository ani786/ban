package com.me.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.Instant;

@Configuration
@EnableWebSecurity
public class SalesforceConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.client.registration.salesforce.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.salesforce.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.salesforce.authorization-grant-type}")
    private String authorizationGrantType;

    @Value("${spring.security.oauth2.client.registration.salesforce.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.salesforce.scope}")
    private String scope;

    @Value("${spring.security.oauth2.client.registration.salesforce.client-name}")
    private String clientName;

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(salesforceClientRegistration());
    }

    private ClientRegistration salesforceClientRegistration() {
        return ClientRegistration.withRegistrationId("salesforce")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientName(clientName)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .redirectUriTemplate(redirectUri)
                .scope(scope)
                .authorizationUri("https://login.salesforce.com/services/oauth2/authorize")
                .tokenUri("https://login.salesforce.com/services/oauth2/token")
                .userInfoUri("https://login.salesforce.com/services/oauth2/userinfo")
                .userNameAttributeName("username")
                .jwkSetUri("https://login.salesforce.com/id/keys")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizedClientService(authorizedClientService())
                .and()
                .oauth2Client();
    }

    // perform validation logic here
    private void validateToken(OAuth2AccessToken accessToken) {
        // Check if the token is expired
        if (accessToken.getExpiresAt().isBefore(Instant.now())) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "The access token has expired", null));
        }
        // Perform additional validation checks as needed
        // ...
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            OAuth2AccessToken accessToken = authorizedClientService().loadAuthorizedClient(userRequest.getClientRegistration().getRegistrationId(), userRequest.getAccessToken().getTokenValue())
                    .getAccessToken();
            validateToken(accessToken);
            return oAuth2User;
        };
    }
}


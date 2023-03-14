package com.me.api.config;

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
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

@Configuration
@EnableWebSecurity
public class SalesforceConfig extends WebSecurityConfigurerAdapter {

    private final String CLIENT_ID = "YOUR_CLIENT_ID_HERE";
    private final String CLIENT_SECRET = "YOUR_CLIENT_SECRET_HERE";
    private final String ACCESS_TOKEN_URI = "https://login.salesforce.com/services/oauth2/token";
    private final String AUTHORIZATION_URI = "https://login.salesforce.com/services/oauth2/authorize";
    private final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/salesforce";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientRepository(authorizedClientRepository())
                .and()
                .oauth2Client();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(salesforceClientRegistration());
    }

    private ClientRegistration salesforceClientRegistration() {
        return ClientRegistration.withRegistrationId("salesforce")
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(REDIRECT_URI)
                .scope("api")
                .authorizationUri(AUTHORIZATION_URI)
                .tokenUri(ACCESS_TOKEN_URI)
                .userInfoUri("https://login.salesforce.com/services/oauth2/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://login.salesforce.com/id/keys")
                .build();
    }
}

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
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

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
}

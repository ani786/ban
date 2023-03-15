package com.me.api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                                     OAuth2AuthorizedClientService authorizedClientService) {
        return restTemplateBuilder
                .interceptors(new OAuth2AuthorizedClientInterceptor(authorizedClientService))
                .build();
    }

    private static class OAuth2AuthorizedClientInterceptor implements ClientHttpRequestInterceptor {

        private final OAuth2AuthorizedClientService authorizedClientService;

        public OAuth2AuthorizedClientInterceptor(OAuth2AuthorizedClientService authorizedClientService) {
            this.authorizedClientService = authorizedClientService;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            request.getHeaders().setBearerAuth(accessToken.getTokenValue());
            return execution.execute(request, body);
        }
    }
}
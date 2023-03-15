package com.me.api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Configuration
public class RestTemplateConfig {

    private final SalesforceConfig salesforceConfig;
    private final OAuth2AuthorizedClientService clientService;

    public RestTemplateConfig(SalesforceConfig salesforceConfig, OAuth2AuthorizedClientService clientService) {
        this.salesforceConfig = salesforceConfig;
        this.clientService = clientService;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .interceptors(oAuth2AuthorizedClientInterceptor())
                .build();
    }

    private ClientHttpRequestInterceptor oAuth2AuthorizedClientInterceptor() {
        return (request, body, execution) -> {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            if (token != null) {
                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                        token.getAuthorizedClientRegistrationId(),
                        token.getName()
                );
                if (client != null) {
                    request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
                }
            }

            URI requestUri = request.getURI();
            String apiBaseUrl = salesforceConfig.getApiBaseUrl();
            if (requestUri.toString().startsWith(apiBaseUrl)) {
                URI newUri = UriComponentsBuilder.fromUri(requestUri)
                        .host(salesforceConfig.getApiHost())
                        .scheme(salesforceConfig.getApiScheme())
                        .build()
                        .toUri();
                request = new HttpRequestWrapper(request) {
                    @Override
                    public URI getURI() {
                        return newUri;
                    }
                };
            }

            return execution.execute(request, body);
        };
    }
}
package com.me.service;

import com.me.api.accountbalance.Account;
import com.me.api.accountbalance.AccountResponse;
import com.me.api.config.SalesforceConfig;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class SalesforceService {

    private final RestTemplate restTemplate;
    private final OAuth2AuthorizedClientService clientService;

    public SalesforceService(RestTemplate restTemplate, OAuth2AuthorizedClientService clientService) {
        this.restTemplate = restTemplate;
        this.clientService = clientService;
    }

    public List<Account> getAccounts() {
        OAuth2AuthorizedClient client = getOAuth2AuthorizedClient();

        String apiUrl = SalesforceConfig.API_BASE_URL.replace("{apiHost}", client.getClientRegistration().getProviderDetails().getConfigurationMetadata().get("instance_url").toString())
                .replace("{apiBasePath}", "/services/data/v" + client.getClientRegistration().getProviderDetails().getConfigurationMetadata().get("version").toString());
        String query = "SELECT Id, Name FROM Account";
        String url = apiUrl + "/query?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);

        ResponseEntity<AccountResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            AccountResponse accountResponse = responseEntity.getBody();
            if (accountResponse != null) {
                return accountResponse.getRecords();
            }
        }
        return Collections.emptyList();
    }

    private OAuth2AuthorizedClient getOAuth2AuthorizedClient() {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(),
                token.getName()
        );
        if (client == null) {
            throw new RuntimeException("Salesforce OAuth2AuthorizedClient not found");
        }
        OAuth2AccessToken accessToken = client.getAccessToken();

        if (Objects.requireNonNull(accessToken.getExpiresAt()).isBefore(Instant.now())) {
            throw new RuntimeException("Salesforce OAuth2AccessToken has expired");
        }
        return client;
    }
}




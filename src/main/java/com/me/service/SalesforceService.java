package com.me.service;

import com.me.api.accountbalance.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
public class SalesforceService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public List<Account> getAccounts() {
        OAuth2AuthenticationToken authentication = getAuthentication();
        String apiUrl = getApiUrl(authentication);

        ResponseEntity<Account[]> response = restTemplateBuilder.build()
                .getForEntity(apiUrl, Account[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        } else {
            throw new RuntimeException("Failed to retrieve accounts from Salesforce");
        }
    }

    private OAuth2AuthenticationToken getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            throw new RuntimeException("User is not authenticated with OAuth2");
        }

        return (OAuth2AuthenticationToken) authentication;
    }

    private String getApiUrl(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        if (client == null) {
            throw new RuntimeException("Failed to load authorized client for Salesforce");
        }

        return client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri()
                + "/query?q=" + UriUtils.encode("SELECT Id, Name FROM Account", StandardCharsets.UTF_8);
    }
}


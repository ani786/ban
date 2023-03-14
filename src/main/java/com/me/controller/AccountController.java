package com.me.controller;

import com.force.api.QueryResult;
import com.me.api.accountbalance.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public List<Account> getAccounts() {
        ResponseEntity<QueryResult<Account>> response = restTemplate.exchange("/query?q={query}", HttpMethod.GET, null, new ParameterizedTypeReference<QueryResult<Account>>() {}, "SELECT Id, Name FROM Account");
        QueryResult<Account> result = response.getBody();
        return result.getRecords();
    }
}

package com.me.controller;

import com.me.api.accountbalance.Account;
import com.me.service.SalesforceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private SalesforceService salesforceService;

    @GetMapping
    public List<Account> getAccounts() {
        return salesforceService.getAccounts();
    }
}


package com.me.api.accountbalance;

import lombok.Data;

import java.util.List;

@Data
public class AccountResponse {

    private List<Account> records;
}

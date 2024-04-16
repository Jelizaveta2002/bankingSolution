package com.example.bankingSolution.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDto {
    private Long id;
    private String customerId;
    private List<BalanceDto> balances;
}

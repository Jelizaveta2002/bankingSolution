package com.example.bankingSolution.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TransactionResponseDto {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private String currency;
    private String direction;
    private String description;
}

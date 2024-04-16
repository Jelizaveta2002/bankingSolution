package com.example.bankingSolution.dto;

import com.example.bankingSolution.classifiers.CurrencyEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDto {
    private BigDecimal amount;
    private CurrencyEnum currency;
}

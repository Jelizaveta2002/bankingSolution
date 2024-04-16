package com.example.bankingSolution.dto;

import com.example.bankingSolution.classifiers.CurrencyEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDtoRequest {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private CurrencyEnum currency;

}

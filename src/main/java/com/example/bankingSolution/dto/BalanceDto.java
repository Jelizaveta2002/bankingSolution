package com.example.bankingSolution.dto;

import com.example.bankingSolution.classifiers.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDto {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long accountId;
    private BigDecimal amount;
    private CurrencyEnum currency;

}

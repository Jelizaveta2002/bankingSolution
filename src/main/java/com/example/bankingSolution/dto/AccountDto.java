package com.example.bankingSolution.dto;


import com.example.bankingSolution.classifiers.CurrencyEnum;
import lombok.Data;

import java.util.List;

@Data
public class AccountDto {
    private Long id;
    private Long customerId;
    private Long countryId;
    private List<CurrencyEnum> currencies;
}

package com.example.bankingSolution.dto;

import com.example.bankingSolution.exceptions.ApplicationException;
import lombok.Data;

import java.util.List;

@Data
public class AccountDtoRequest {
    private Long id;
    private Long customerId;
    private Long countryId;
    private List<String> currencies;

    public void setCustomerId(Long customerId) {
        if (customerId == null) {
            throw new ApplicationException("Customer ID cannot be null.");
        }
        this.customerId = customerId;
    }

    public void setCountryId(Long countryId) {
        if (countryId == null) {
            throw new ApplicationException("Country ID cannot be null.");
        }
        this.countryId = countryId;
    }

    public void setCurrencies(List<String> currencies) {
        if (currencies == null) {
            throw new ApplicationException("Currencies cannot be null.");
        }
        this.currencies = currencies;
    }

    public AccountDtoRequest(Long customerId, Long countryId, List<String> currencies) {
        setCustomerId(customerId);
        setCountryId(countryId);
        setCurrencies(currencies);
    }
}

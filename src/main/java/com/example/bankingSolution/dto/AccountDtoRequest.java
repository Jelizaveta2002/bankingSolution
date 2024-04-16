package com.example.bankingSolution.dto;

import com.example.bankingSolution.exceptions.ApplicationException;
import lombok.Data;

import java.util.List;

@Data
public class AccountDtoRequest {
    private Long id;
    private Long customerId;
    private String country;
    private List<String> currencies;

    public void setCustomerId(Long customerId) {
        if (customerId == null) {
            throw new ApplicationException("Customer ID cannot be null.");
        }
        this.customerId = customerId;
    }

        public void setCountry(String country) {
        if (country == null) {
            throw new ApplicationException("Country cannot be null.");
        }
        this.country = country;
    }

    public void setCurrencies(List<String> currencies) {
        if (currencies == null) {
            throw new ApplicationException("Currencies cannot be null.");
        }
        this.currencies = currencies;
    }

    public AccountDtoRequest(Long customerId, String country, List<String> currencies) {
        setCustomerId(customerId);
        setCountry(country);
        setCurrencies(currencies);
    }
}

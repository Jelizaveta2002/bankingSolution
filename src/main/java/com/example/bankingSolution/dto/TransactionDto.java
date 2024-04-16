package com.example.bankingSolution.dto;

import com.example.bankingSolution.exceptions.ApplicationException;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private String currency;
    private String direction;
    private String description;
    private BalanceDto balanceAfterTransaction;

    public void setAccountId(Long accountId) {
        if (accountId == null) {
            throw new ApplicationException("Account ID cannot be null.");
        }
        this.accountId = accountId;
    }

    public void setAmount(BigDecimal amount) {
        if (amount == null) {
            throw new ApplicationException("Amount cannot be null.");
        }
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        if (currency == null) {
            throw new ApplicationException("Currency cannot be null.");
        }
        this.currency = currency;
    }

    public void setDirection(String direction) {
        if (direction == null) {
            throw new ApplicationException("Direction cannot be null.");
        }
        this.direction = direction;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new ApplicationException("Description cannot be null.");
        }
        this.description = description;
    }

    public TransactionDto(Long accountId, BigDecimal amount, String currency, String direction, String description) {
        setAccountId(accountId);
        setAmount(amount);
        setCurrency(currency);
        setDirection(direction);
        setDescription(description);
    }

}

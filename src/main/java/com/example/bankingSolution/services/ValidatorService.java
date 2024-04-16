package com.example.bankingSolution.services;

import com.example.bankingSolution.classifiers.CurrencyEnum;
import com.example.bankingSolution.classifiers.DirectionEnum;
import com.example.bankingSolution.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ValidatorService {
    public void validateCurrency(String currency) throws ApplicationException {
        try {
            CurrencyEnum currencyEnum = CurrencyEnum.valueOf(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Invalid currency: " + currency);
        }
    }

    public void validateDirection(String direction) throws ApplicationException {
        try {
            DirectionEnum directionEnum = DirectionEnum.valueOf(direction.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Invalid direction: " + direction);
        }
    }

    public void validateAmount(DirectionEnum direction, BigDecimal amount, BigDecimal balanceAmount) throws ApplicationException {
        try {
            if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
                throw new ApplicationException("Invalid amount: " + amount);
            } else if (amount.compareTo(balanceAmount) > 0 && direction == DirectionEnum.OUT) {
                throw new ApplicationException("Insufficient funds");
            }
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Unexpected problem occurred while transaction");
        }
    }
}

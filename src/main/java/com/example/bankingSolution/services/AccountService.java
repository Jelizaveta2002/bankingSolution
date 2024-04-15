package com.example.bankingSolution.services;

import com.example.bankingSolution.classifiers.CurrencyEnum;
import com.example.bankingSolution.dao.AccountDao;
import com.example.bankingSolution.dao.BalanceDao;
import com.example.bankingSolution.dto.AccountDto;
import com.example.bankingSolution.dto.BalanceDto;
import com.example.bankingSolution.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountDao accountDao;
    private final BalanceDao balanceDao;

    public void createAccount(AccountDto account) throws ApplicationException{
        validateCurrencies(account.getCurrencies());
        try{
            accountDao.createAccount(account);
            log.info(account.getId().toString());
            for (String currency: account.getCurrencies()) {
                BalanceDto balanceDto = new BalanceDto();
                balanceDto.setAccountId(account.getId());
                balanceDto.setCurrency(CurrencyEnum.valueOf(currency.toUpperCase()));
                balanceDto.setAmount(BigDecimal.valueOf(0));
                balanceDao.createBalance(balanceDto);
            }
        } catch (Exception e) {
            throw new ApplicationException("Unexpected error occurred while creating a new account");
        }
    }

    private void validateCurrencies(List<String> currencies) throws ApplicationException {
        for (String currencyStr : currencies) {
            try {
                CurrencyEnum currencyEnum = CurrencyEnum.valueOf(currencyStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ApplicationException("Invalid currency detected: " + currencyStr);
            }
        }
    }
}

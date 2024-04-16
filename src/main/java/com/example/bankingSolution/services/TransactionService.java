package com.example.bankingSolution.services;

import com.example.bankingSolution.classifiers.DirectionEnum;
import com.example.bankingSolution.dao.AccountDao;
import com.example.bankingSolution.dao.BalanceDao;
import com.example.bankingSolution.dao.TransactionDao;
import com.example.bankingSolution.dto.AccountDto;
import com.example.bankingSolution.dto.BalanceDtoRequest;
import com.example.bankingSolution.dto.TransactionDto;
import com.example.bankingSolution.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionDao transactionDao;
    private final AccountDao accountDao;
    private final BalanceDao balanceDao;
    private final AccountService accountService;
    private final ValidatorService validatorService;

    public void createTransaction(TransactionDto transactionDto) throws ApplicationException {
        AccountDto accountDto = accountService.getAccountById(transactionDto.getAccountId());
        validatorService.validateCurrency(transactionDto.getCurrency());
        validatorService.validateDirection(transactionDto.getDirection());
        BalanceDtoRequest balanceDto = balanceDao.getBalanceByIdAndCurrency(accountDto.getId(), transactionDto.getCurrency());
        if (balanceDto != null) {
            validatorService.validateAmount(DirectionEnum.valueOf(transactionDto.getDirection()), transactionDto.getAmount(), balanceDto.getAmount());
            transactionDao.createTransaction(transactionDto);
            if (Objects.equals(transactionDto.getDirection(), "IN")) {
                balanceDao.updateBalanceAmount(balanceDto.getId(), balanceDto.getAmount().add(transactionDto.getAmount()));
            } else {
                balanceDao.updateBalanceAmount(balanceDto.getId(), balanceDto.getAmount().subtract(transactionDto.getAmount()));
            }
        } else {
            throw new ApplicationException("Account with ID " + transactionDto.getAccountId() + " does not have balance with currency " + transactionDto.getCurrency());
        }
    }

}

package com.example.bankingSolution.services;

import com.example.bankingSolution.classifiers.DirectionEnum;
import com.example.bankingSolution.dao.AccountDao;
import com.example.bankingSolution.dao.BalanceDao;
import com.example.bankingSolution.dao.TransactionDao;
import com.example.bankingSolution.dto.AccountDto;
import com.example.bankingSolution.dto.BalanceDto;
import com.example.bankingSolution.dto.TransactionDto;
import com.example.bankingSolution.dto.TransactionResponseDto;
import com.example.bankingSolution.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionDao transactionDao;
    private final BalanceDao balanceDao;
    private final AccountService accountService;
    private final ValidatorService validatorService;
    private final AccountDao accountDao;

    public TransactionDto createTransaction(TransactionDto transactionDto) throws ApplicationException {
        AccountDto account = accountService.getAccountById(transactionDto.getAccountId());
        validatorService.validateCurrency(transactionDto.getCurrency());
        validatorService.validateDirection(transactionDto.getDirection());
        BalanceDto balanceDto = balanceDao.getBalanceByIdAndCurrency(account.getId(), transactionDto.getCurrency());
        if (balanceDto != null) {
            validatorService.validateAmount(DirectionEnum.valueOf(transactionDto.getDirection()), transactionDto.getAmount(), balanceDto.getAmount());
            if (Objects.equals(transactionDto.getDirection(), "IN")) {
                balanceDao.updateBalanceAmount(balanceDto.getId(), balanceDto.getAmount().add(transactionDto.getAmount()));
            } else {
                balanceDao.updateBalanceAmount(balanceDto.getId(), balanceDto.getAmount().subtract(transactionDto.getAmount()));
            }
            BalanceDto transactionBalance = balanceDao.getBalanceByIdAndCurrency(transactionDto.getAccountId(), transactionDto.getCurrency());
            transactionDto.setBalanceAfterTransaction(transactionBalance);
            transactionDao.createTransaction(transactionDto);
            return transactionDto;
        } else {
            throw new ApplicationException("Account with ID " + transactionDto.getAccountId() + " does not have balance with currency " + transactionDto.getCurrency());
        }
    }

    public List<TransactionResponseDto> getAllTransactionsByAccountId(Long id) {
        if (accountDao.getAccountById(id).isPresent()) {  //check if account is present
            return transactionDao.getAllTransactionsByAccountId(id);
        } else {
            throw new ApplicationException("No account with ID: " + id);
        }
    }

}

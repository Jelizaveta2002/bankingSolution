package com.example.bankingSolution.services;

import com.example.bankingSolution.dao.AccountDao;
import com.example.bankingSolution.dto.AccountDto;
import com.example.bankingSolution.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;

    public void createAccount(AccountDto account) {
        try{
            accountDao.createAccount(account);
        } catch (Exception e) {
            throw new ApplicationException("Unexpected error occurred while creating a new account");
        }
    }
}

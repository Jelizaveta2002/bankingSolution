package com.example.bankingSolution.dao;

import com.example.bankingSolution.dto.AccountDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDao {
    void createAccount(AccountDto account);
}

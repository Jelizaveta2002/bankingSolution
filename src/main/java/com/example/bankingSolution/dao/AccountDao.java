package com.example.bankingSolution.dao;

import com.example.bankingSolution.dto.AccountDto;
import com.example.bankingSolution.dto.AccountDtoRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AccountDao {
    void createAccount(AccountDtoRequest account);
    Optional<AccountDto> getAccountById(@Param("id") Long id);
}

package com.example.bankingSolution.dao;

import com.example.bankingSolution.dto.BalanceDto;
import com.example.bankingSolution.dto.BalanceDtoRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BalanceDao {
    void createBalance(BalanceDtoRequest balanceDto);
    List<BalanceDto> getAllBalancesByAccountId(@Param("id") Long id);
    BalanceDtoRequest getBalanceByIdAndCurrency(@Param("id") Long id, @Param("currency") String currency);

    void updateBalanceAmount(@Param("id") Long id, @Param("amount") BigDecimal amount);
}

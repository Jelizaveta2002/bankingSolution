package com.example.bankingSolution.dao;

import com.example.bankingSolution.dto.BalanceDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BalanceDao {
    void createBalance(BalanceDto balanceDto);
}

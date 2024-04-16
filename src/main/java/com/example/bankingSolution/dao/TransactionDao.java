package com.example.bankingSolution.dao;

import com.example.bankingSolution.dto.TransactionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransactionDao {
   void createTransaction(TransactionDto transactionDto);
   TransactionDto getTransactionById(@Param("id") Long id);
}

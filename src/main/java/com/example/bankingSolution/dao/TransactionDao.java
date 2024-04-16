package com.example.bankingSolution.dao;

import com.example.bankingSolution.dto.TransactionDto;
import com.example.bankingSolution.dto.TransactionResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransactionDao {
   void createTransaction(TransactionDto transactionDto);
   TransactionDto getTransactionById(@Param("id") Long id);
   List<TransactionResponseDto> getAllTransactionsByAccountId(@Param("accountId") Long accountId);
}

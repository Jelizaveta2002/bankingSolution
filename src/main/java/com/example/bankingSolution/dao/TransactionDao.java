package com.example.bankingSolution.dao;

import com.example.bankingSolution.dto.TransactionDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionDao {
   void createTransaction(TransactionDto transactionDto);
}

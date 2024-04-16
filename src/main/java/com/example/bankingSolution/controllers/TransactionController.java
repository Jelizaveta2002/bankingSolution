package com.example.bankingSolution.controllers;

import com.example.bankingSolution.dto.TransactionDto;
import com.example.bankingSolution.dto.TransactionResponseDto;
import com.example.bankingSolution.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto addTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }

    @GetMapping("/get/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponseDto> getTransactions(@PathVariable Long accountId) {
        return transactionService.getAllTransactionsByAccountId(accountId);
    }
}

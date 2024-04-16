package com.example.bankingSolution.controllers;

import com.example.bankingSolution.dto.AccountDto;
import com.example.bankingSolution.dto.AccountDtoRequest;
import com.example.bankingSolution.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto addAccount(@RequestBody AccountDtoRequest account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/get/{id}")
    public AccountDto getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }
}

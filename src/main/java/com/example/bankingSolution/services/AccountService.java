package com.example.bankingSolution.services;

import com.example.bankingSolution.classifiers.CurrencyEnum;
import com.example.bankingSolution.dao.AccountDao;
import com.example.bankingSolution.dao.BalanceDao;
import com.example.bankingSolution.dto.AccountDto;
import com.example.bankingSolution.dto.AccountDtoRequest;
import com.example.bankingSolution.dto.BalanceDto;
import com.example.bankingSolution.exceptions.ApplicationException;
import com.example.bankingSolution.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountDao accountDao;
    private final BalanceDao balanceDao;
    private final ValidatorService validatorService;
    private final RabbitTemplate rabbitTemplate;

    public AccountDto createAccount(AccountDtoRequest accountDtoRequest) throws ApplicationException {
        validatorService.validateCountry(accountDtoRequest.getCountry());
        for (String currencyStr : accountDtoRequest.getCurrencies()) {
            validatorService.validateCurrency(currencyStr);
        }
        try {
            accountDao.createAccount(accountDtoRequest);
            log.info(accountDtoRequest.getId().toString());
            for (String currency : accountDtoRequest.getCurrencies()) {
                BalanceDto balanceDto = new BalanceDto();
                balanceDto.setAccountId(accountDtoRequest.getId());
                balanceDto.setCurrency(CurrencyEnum.valueOf(currency.toUpperCase()));
                balanceDto.setAmount(BigDecimal.valueOf(0));
                balanceDao.createBalance(balanceDto);
            }
            AccountDto account = getAccountById(accountDtoRequest.getId());
            rabbitTemplate.convertAndSend(RabbitMQConfig.ACCOUNT_EXCHANGE_NAME, RabbitMQConfig.ACCOUNT_ROUTING_KEY, account);
            return account;
        } catch (Exception e) {
            throw new ApplicationException("Unexpected error occurred while creating a new accountDtoRequest");
        }
    }

    public AccountDto getAccountById(Long id) {
        Optional<AccountDto> accountDtoOptional = accountDao.getAccountById(id);
        if (accountDtoOptional.isPresent()) {
            AccountDto accountDto = accountDtoOptional.get();
            List<BalanceDto> balances = balanceDao.getAllBalancesByAccountId(accountDto.getId());
            accountDto.setBalances(balances);
            return accountDto;
        }
        else {
            throw new ApplicationException("No account with id " + id);
        }
    }

}

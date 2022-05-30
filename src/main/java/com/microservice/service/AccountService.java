package com.microservice.service;

import com.microservice.model.Account;
import com.microservice.repository.AccountRepository;
import com.microservice.service.impl.AccountServiceImpl;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceImpl {

    private  final AccountRepository accountRepository;

    @Override
    public Flux<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> getByIdAccount(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        account.setStatusAccount("Activo");
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> updateAccount(String id, Account account) {
        return accountRepository.findById(id).flatMap(account1 -> {
            account1.setAvailableBalanceAccount(account.getAvailableBalanceAccount());
            account1.setStatusAccount(account.getStatusAccount());
            return accountRepository.save(account1);
        }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> deleteAccount(String id) {
        return accountRepository.findById(id).flatMap(account -> accountRepository.deleteById(account.getId()));
    }
}

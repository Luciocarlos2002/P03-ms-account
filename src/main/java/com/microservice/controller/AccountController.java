package com.microservice.controller;

import com.microservice.model.Account;
import com.microservice.service.AccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController{

    private final AccountService accountService;

    private static final String ACCOUNT = "account";

    @GetMapping(value = "/allAccounts")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Account> getAllAccounts(){
        System.out.println("Listar todas las cuentas bancarias.");
        return accountService.getAllAccounts();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Account> getAccountById(@PathVariable String id){
        System.out.println("Buscar cuenta bancaria por ID.");
        return accountService.getByIdAccount(id);
    }

    @PostMapping(value = "/create")
    @CircuitBreaker(name = ACCOUNT, fallbackMethod = "account")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> createAccount(@RequestBody Account account){
        System.out.println("Cuenta bancaria creada con Éxito.");
        return accountService.createAccount(account);
    }

    @PutMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = ACCOUNT, fallbackMethod = "account")
    public Mono<Account> updateAccount(@PathVariable String id, @RequestBody Account account){
        System.out.println("Cuenta bancaria actualizada con Éxito.");
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteAccount(@PathVariable String id){
        System.out.println("Cuenta bancaria eliminada.");
        return accountService.deleteAccount(id);
    }
}

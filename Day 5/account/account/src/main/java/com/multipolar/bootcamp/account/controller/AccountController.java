package com.multipolar.bootcamp.account.controller;

import com.multipolar.bootcamp.account.domain.Account;
import com.multipolar.bootcamp.account.dto.ErrorMessage;
import com.multipolar.bootcamp.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")

public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {

        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ErrorMessage> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode("VALIDATION_ERROR");
                errorMessage.setMessage(error.getDefaultMessage());
                validationErrors.add(errorMessage);
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }

        Account createAccount = accountService.createOrUpdate(account);
        return ResponseEntity.ok(createAccount);
    }


    @GetMapping
    public List<Account> getAllAccount(){

        return accountService.getAllAccount();
    }

    //get acc by id lewat pathvariabel/segment
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable String id){
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //edit acc
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable String id, @RequestBody Account account){
        account.setId(id);
        return accountService.createOrUpdate(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable String id){
        accountService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }

    //get acc by nik
    @GetMapping("/nik/{nik}")
    public ResponseEntity<Account> getAccountByNik(@PathVariable String nik) {
        Optional<Account> account = accountService.getAccountByAccountHolderNik(nik);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    //get acc by acc num
    @GetMapping("/accnum/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber){
        Optional<Account> account = accountService.getAccountByAccountNumber(accountNumber);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //get acc by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Account>> getAccountsByName(@PathVariable String name) {
        List<Account> accounts = accountService.getAccountByName(name);
        return ResponseEntity.ok(accounts);
    }

}

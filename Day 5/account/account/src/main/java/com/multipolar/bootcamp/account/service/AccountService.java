package com.multipolar.bootcamp.account.service;

import com.multipolar.bootcamp.account.domain.Account;
import com.multipolar.bootcamp.account.domain.AccountHolder;
import com.multipolar.bootcamp.account.repository.AccountRepository;
import com.multipolar.bootcamp.account.domain.Account;
import com.multipolar.bootcamp.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    // fungsi untuk get all account
    public List<Account> getAllAccount(){

        return accountRepository.findAll();
    }

    //fungsi untuk get account by id
    public Optional<Account> getAccountById(String id){

        return accountRepository.findById(id);
    }

    //fungsi untuk create account baru
    public Account createOrUpdate(Account account){

        return accountRepository.save(account);
    }


    //fungsi untuk delete product
    public void deleteAccountById(String id){

        accountRepository.deleteById(id);
    }

    public Optional<Account> getAccountByAccountHolderNik(String nik){

        Optional<Account> accountHolder = accountRepository.findByAccountHolderNik(nik);
        return accountRepository.findByAccountHolderNik(nik);
    }

    public Optional<Account> getAccountByAccountNumber(String accountNumber){

        return accountRepository.findByAccountNumber(accountNumber);
    }

    public List<Account> getAccountByName(String name){

        return accountRepository.getByName(name);
    }
}

package com.multipolar.bootcamp.account.repository;

import com.multipolar.bootcamp.account.domain.Account;
import com.multipolar.bootcamp.account.domain.Account;
import com.multipolar.bootcamp.account.domain.AccountHolder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account,String> {
//    Optional<AccountHolder> findByNik(String nik);
    Optional<Account> findByAccountHolderNik(String nik);
    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("{ 'accountHolder.name' : { $regex: ?0, $options: 'i' } }")
    List<Account> getByName(String name);

}
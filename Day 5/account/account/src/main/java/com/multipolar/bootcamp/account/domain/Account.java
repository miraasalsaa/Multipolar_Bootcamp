package com.multipolar.bootcamp.account.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document

public class Account implements Serializable {

    @Id
    private String id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private AccountHolder accountHolder;
    private Double balance;
    private LocalDateTime openingDate = LocalDateTime.now();
    private LocalDate closingDate;

}

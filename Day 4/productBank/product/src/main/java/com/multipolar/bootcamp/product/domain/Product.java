package com.multipolar.bootcamp.product.domain;

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

public class Product implements Serializable {

    @Id
    private String id;
    private String productName;
    private ProductType productType;
    private Double interestRate;
    private Double minimumBalance;
    private Double maximumLoanAmount;
    private String termAndConditions;
    private LocalDateTime dateCreated = LocalDateTime.now();

}

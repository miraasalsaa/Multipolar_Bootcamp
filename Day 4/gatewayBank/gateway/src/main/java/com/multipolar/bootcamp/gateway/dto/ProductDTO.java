package com.multipolar.bootcamp.gateway.dto;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class ProductDTO implements Serializable {

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
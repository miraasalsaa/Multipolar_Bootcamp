package com.multipolar.bootcamp.kyc.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
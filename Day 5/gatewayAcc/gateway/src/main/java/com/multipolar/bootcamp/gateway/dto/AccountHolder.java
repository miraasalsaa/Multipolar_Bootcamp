package com.multipolar.bootcamp.gateway.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountHolder implements Serializable {
    @NotEmpty(message = "NIK tidak boleh kosong")
    @Length(min=16, max=16, message = "Jumlah digit harus 16")
    private String nik;
    private String name;
    private String address;
}
package com.multipolar.bootcamp.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Todo implements Serializable {
    //serializable : pengiriman objek agar dapat dikirim dengan baik

    private int id;
    private String task;

}

package com.multipolar.bootcamp.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Product implements Serializable {
    private int id;
    private String name;

}

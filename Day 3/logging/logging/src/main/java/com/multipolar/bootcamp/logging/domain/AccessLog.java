package com.multipolar.bootcamp.logging.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "access_log")

public class AccessLog implements Serializable {

    private String id;
    private String method;
    private String status;
    private String message;
}
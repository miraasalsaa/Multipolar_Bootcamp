package com.multipolar.bootcamp.logging.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "access_log_product")

public class AccessLog implements Serializable {

    private String id;
    private String requestMethod;
    private String requestUrl;
    private String content;
    private Integer responseStatusCode;
    private LocalDateTime timeStamp = LocalDateTime.now();
}
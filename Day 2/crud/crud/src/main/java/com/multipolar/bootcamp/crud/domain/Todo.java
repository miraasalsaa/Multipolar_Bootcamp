package com.multipolar.bootcamp.crud.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document

public class Todo implements Serializable {

    @Id
    private String id;

    @NotEmpty(message = "Task must not be empty")
    private String task;

    private String description;
    private LocalDateTime dueDate;
    private Status status;
    private Priority priority;
    private List<String> tags;
}

package com.multipolar.bootcamp.kyc.controller;

import com.multipolar.bootcamp.kyc.domain.Todo;
import com.multipolar.bootcamp.kyc.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kyc")
@Validated

public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createOrUpdate(todo);
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return todoService.getAllTodos();
    }

    //get todo by id lewat pathvariabel/segment
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id){
        Optional<Todo> todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //edit todo
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id,@RequestBody Todo todo){
        todo.setId(id);
        return todoService.createOrUpdate(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable String id){
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }
}

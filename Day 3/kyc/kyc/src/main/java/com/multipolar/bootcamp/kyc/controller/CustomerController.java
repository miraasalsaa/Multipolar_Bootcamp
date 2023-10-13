package com.multipolar.bootcamp.kyc.controller;

import com.multipolar.bootcamp.kyc.domain.Customer;
import com.multipolar.bootcamp.kyc.dto.ErrorMessage;
import com.multipolar.bootcamp.kyc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kyc")

public class CustomerController {

    private final CustomerService todoService;

    @Autowired
    public CustomerController(CustomerService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@Valid @RequestBody Customer todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ErrorMessage> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode("VALIDATION_ERROR");
                errorMessage.setMessage(error.getDefaultMessage());
                validationErrors.add(errorMessage);
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }

        Customer createdTodo = todoService.createOrUpdate(todo);
        return ResponseEntity.ok(createdTodo);
    }


    @GetMapping
    public List<Customer> getAllTodos(){
        return todoService.getAllTodos();
    }

    //get todo by id lewat pathvariabel/segment
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getTodoById(@PathVariable String id){
        Optional<Customer> todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //edit todo
    @PutMapping("/{id}")
    public Customer updateTodo(@PathVariable String id, @RequestBody Customer todo){
        todo.setId(id);
        return todoService.createOrUpdate(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable String id){
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }

    //get todo by nik
    @GetMapping("/nik/{nik}")
    public ResponseEntity<Customer> getTodoByNIK(@PathVariable String nik){
        Optional<Customer> todo = todoService.getTodoByNIK(nik);
        return todo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //get todo by name
    @GetMapping("/name/{name}")
    public List<Customer> getTodoByName(@PathVariable String name){

        return todoService.getTodoByName(name);
    }
}

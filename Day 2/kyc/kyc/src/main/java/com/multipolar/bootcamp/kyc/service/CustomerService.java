package com.multipolar.bootcamp.kyc.service;

import com.multipolar.bootcamp.kyc.domain.Customer;
import com.multipolar.bootcamp.kyc.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CustomerService {
    private final CustomerRepository todoRepository;

    @Autowired
    public CustomerService(CustomerRepository todoRepository) {

        this.todoRepository = todoRepository;
    }

    // fungsi untuk get all todos
    public List<Customer> getAllTodos(){

        return todoRepository.findAll();
    }

    //fungsi untuk get todo by id
    public Optional<Customer> getTodoById(String id){

        return todoRepository.findById(id);
    }

    //fungsi untuk create todo baru
    public Customer createOrUpdate(Customer todo){

        return todoRepository.save(todo);
    }

    //fungsi untuk delete todo
    public void deleteTodoById(String id){

        todoRepository.deleteById(id);
    }

    public Optional<Customer> getTodoByNIK(String nik){
        return todoRepository.findByNik(nik);
    }

    public List<Customer> getTodoByName(String name){
        return todoRepository.getByName(name);
    }
}

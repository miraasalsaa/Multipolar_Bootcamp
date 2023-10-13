package com.multipolar.bootcamp.product.controller;

import com.multipolar.bootcamp.product.domain.Product;
import com.multipolar.bootcamp.product.dto.ErrorMessage;
import com.multipolar.bootcamp.product.service.ProductService;
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
@RequestMapping("/product")

public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
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

        Product createdTodo = productService.createOrUpdate(product);
        return ResponseEntity.ok(createdTodo);
    }


    @GetMapping
    public List<Product> getAllProduct(){

        return productService.getAllProduct();
    }

    //get product by id lewat pathvariabel/segment
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //edit todo
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product){
        product.setId(id);
        return productService.createOrUpdate(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

//    get todo by nik
//    @GetMapping("/nik/{nik}")
//    public ResponseEntity<Product> getTodoByNIK(@PathVariable String nik){
//        Optional<Product> todo = productService.getTodoByNik(nik);
//        return todo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }

    //get todo by name
    @GetMapping("/name/{productName}")
    public List<Product> getProductByName(@PathVariable String productName){

        return productService.getProductByName(productName);
    }
}

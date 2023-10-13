package com.multipolar.bootcamp.product.service;

import com.multipolar.bootcamp.product.domain.Product;
import com.multipolar.bootcamp.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    // fungsi untuk get all product
    public List<Product> getAllProduct(){

        return productRepository.findAll();
    }

    //fungsi untuk get product by id
    public Optional<Product> getProductById(String id){

        return productRepository.findById(id);
    }

    //fungsi untuk create product baru
    public Product createOrUpdate(Product product){

        return productRepository.save(product);
    }

    //fungsi untuk delete product
    public void deleteProductById(String id){

        productRepository.deleteById(id);
    }

//    public Optional<Product> getTodoByNIK(String nik){
//
//        return productRepository.findByNik(nik);
//    }

    public List<Product> getProductByName(String productName){

        return productRepository.getByName(productName);
    }
}

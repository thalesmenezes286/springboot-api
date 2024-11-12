package com.dev.productsapi.controllers;

import com.dev.productsapi.dto.ProductRecordDTO;
import com.dev.productsapi.models.ProductModel;
import com.dev.productsapi.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDTO, productModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>>getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id){
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updatePoduct(@PathVariable(value = "id")UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO){
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        var productModel = product.get();
        BeanUtils.copyProperties(productRecordDTO, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id")UUID id){
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        productRepository.delete(product.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product delete!");
    }
}

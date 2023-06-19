package com.szklarnia.controller;

import com.szklarnia.model.Product;
import com.szklarnia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //delete by ID
    @DeleteMapping("/{productId}") //{} musi być to, co w 31 linii
    public ResponseEntity<Void> deleteProductById(@PathVariable int productId) {
        if(productService.getProductById(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            productService.deleteProductById(productId);
            return ResponseEntity.noContent().build();
        }
    }

    //get by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        Optional<Product> responseFromProduct = productService.getProductById(productId);
        if(responseFromProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(responseFromProduct.get());
        }
    }

    //post/create new entity (nie podawać ID w postmanie, bo jest ustawiony autoincrement w My SQL Workbench
    @PostMapping
    public ResponseEntity<Product> postNewProduct(@RequestBody Product newProduct) {
        Optional <Product> savedProduct = productService.postNewProduct(newProduct);
        //by nie można było nadpisać ID
        if(savedProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // dla nulla
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct.get());
        }
    }

    @PutMapping("/completeUpdate/{productId}")
    public ResponseEntity<String> completeProductEntityUpdate(@PathVariable Integer productId, @RequestBody Product updatedProduct) {
        Optional<Product> productToBeUpdated = productService.completeProductEntityUpdate(productId, updatedProduct);
        if(productToBeUpdated.isPresent()) {
            return ResponseEntity.ok("product updated successfully!"); //dlatego ResponseEntity przyjmuje typ String
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

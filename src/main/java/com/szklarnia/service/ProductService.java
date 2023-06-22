package com.szklarnia.service;


import com.szklarnia.model.GrowerCompany;
import com.szklarnia.model.Product;
import com.szklarnia.repository.GrowerCompanyRepository;
import com.szklarnia.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public GrowerCompanyRepository growerCompanyRepository;

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public void deleteProductById(int productId) {
        productRepository.deleteById(productId);
    }


    public Optional<Product> getProductById(int productId) {
        return productRepository.findById(productId);
    }


    public Optional<Product> postNewProduct(Product newProduct) {
        if(newProduct.getProductId() != null && productRepository.existsById(newProduct.getProductId())) {
            return Optional.empty();
        }
        return Optional.of(productRepository.save(newProduct));
    }


    public Optional<Product> completeProductEntityUpdate(Integer productId, Product updateProduct) {

        if(productRepository.existsById(productId)) { //existById z CrudRepository
            updateProduct.setProductId(productId); //napisanie starego ID
            return Optional.of(productRepository.save(updateProduct)); //save z CrudRepository
        }
        return Optional.empty(); //by nie zwracał nulla
    }

    //patch
    public Optional<Product> setGrowerCompanyForProduct(Integer growerCompanyId, Integer productId) {
        if(growerCompanyRepository.existsById(growerCompanyId) && productRepository.existsById(productId)) {
            Product product = productRepository.findById(productId).get();
            GrowerCompany growerCompany = growerCompanyRepository.findById(growerCompanyId).get();
            product.addGrowerCompany(growerCompany);
            return Optional.of(productRepository.save(product));
        }
        return Optional.empty();
    }

}

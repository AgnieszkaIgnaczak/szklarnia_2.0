package com.szklarnia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;


    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonIgnore
    //@ManyToMany(mappedBy = "products")
    @JoinTable(name = "grower_company_product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "company_id")) //jeśli są 2 metody w obie strony
    private Set<GrowerCompany> growerCompanies;

    private String fruit;

    private String vegetable;

    private String flower;

    private String seedling;

    public Product() {

    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public String getVegetable() {
        return vegetable;
    }

    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }

    public String getFlower() {
        return flower;
    }

    public void setFlower(String flower) {
        this.flower = flower;
    }

    public String getSeedling() {
        return seedling;
    }

    public void setSeedling(String seedling) {
        this.seedling = seedling;
    }

    public Set<GrowerCompany> getGrowerCompanies() {
        return growerCompanies;
    }

    public void setGrowerCompanies(Set<GrowerCompany> growerCompanies) {
        this.growerCompanies = growerCompanies;
    }

    public void addGrowerCompany(GrowerCompany growerCompany) {
        this.growerCompanies.add(growerCompany);
    }

}

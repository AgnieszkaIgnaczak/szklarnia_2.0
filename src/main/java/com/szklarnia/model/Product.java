package com.szklarnia.model;

public class Product {

    private Integer productId;
    private String fruit;
    private String vegetable;
    private String flower;
    private String seedling;

    public Product(Integer productId, String fruit, String vegetable, String flower, String seedling) {
        this.productId = productId;
        this.fruit = fruit;
        this.vegetable = vegetable;
        this.flower = flower;
        this.seedling = seedling;
    }

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
}

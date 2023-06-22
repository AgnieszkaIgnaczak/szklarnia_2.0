package com.szklarnia.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

@Entity
@Table
public class GrowerCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @OneToMany(mappedBy = "growerCompany") //pole w encji Greenhouse
    @JsonIgnore
    private List<Greenhouse> greenhouses;


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "grower_company_product", joinColumns = @JoinColumn(name = "company_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    public Set<Product> products;

    @NotNull(message = "name cannot be null")
    private String name;
    private String address;
    private String phoneNumber;
    private String EMail;


    public GrowerCompany() {

    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public List<Greenhouse> getGreenhouses() {
        return greenhouses;
    }

    public void setGreenhouses(List<Greenhouse> greenhouses) {
        this.greenhouses = greenhouses;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

}

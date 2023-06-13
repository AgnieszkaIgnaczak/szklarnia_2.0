package com.szklarnia.model;

public class Gardener {

    private Integer gardenerId;
    private String name;
    private Float experience;
    private String address;
    private Float salary;

    public Gardener(Integer gardenerId, String name, Float experience, String address, Float salary) {
        this.gardenerId = gardenerId;
        this.name = name;
        this.experience = experience;
        this.address = address;
        this.salary = salary;
    }

    public Gardener() {

    }

    public Integer getGardenerId() {
        return gardenerId;
    }

    public void setGardenerId(Integer gardenerId) {
        this.gardenerId = gardenerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getExperience() {
        return experience;
    }

    public void setExperience(Float experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }
}

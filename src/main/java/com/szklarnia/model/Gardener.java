package com.szklarnia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table //(name = "NAZWA TABELI")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Data (Gettery i Settery)
public class Gardener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //parent
    @Column(name = "gardener_id") //primary key dla ogrodnika
    private Integer gardenerId;

    //każdy ogrodnik ma tylko 1 szklarnię
    //stworzy z automatu kolumnę z foreign key w tabeli Gardener: greenhouse_greenhouse_id (greenhouse_id to primary key z encji Greenhouse)
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //ALL zawiera wszystkie, oprócz REMOVE, bo REMOVE jeśli usunę szklarnię to usunie wszystko
    @JoinColumn(name = "fk_greenhouse_id") //w momencie, gdy chcemy nadać nazwę kolumnie z foreign key
    private Greenhouse greenhouse; //pole typu Greenhouse, żeby połączył encje

    @NotNull(message = "name cannot be null")
    private String name;


    private Integer experience;


    private String address;

    @NotNull(message = "name cannot be null")
    private Float salary;


    public Gardener() {

    }

    public Gardener(String name, Integer experience, String address, Float salary) {
        this.name = name;
        this.experience = experience;
        this.address = address;
        this.salary = salary;
    }

    public Gardener(Integer gardenerId, String name, Integer experience, String address, Float salary) {
        this.gardenerId = gardenerId;
        this.name = name;
        this.experience = experience;
        this.address = address;
        this.salary = salary;
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

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
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

    public Greenhouse getGreenhouse() {
        return greenhouse;
    }

    public void setGreenhouse(Greenhouse greenhouse) {
        this.greenhouse = greenhouse;
    }
}

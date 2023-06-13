package com.szklarnia.model;

public class GrowerCompany {

    private Integer companyId;
    private String name;
    private String address;
    private String phoneNumber;
    private String EMail;

    public GrowerCompany(Integer companyId, String name, String address, String phoneNumber, String EMail) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.EMail = EMail;
    }

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
}

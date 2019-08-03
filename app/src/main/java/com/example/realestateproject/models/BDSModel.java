package com.example.realestateproject.models;

public class BDSModel {
    private String id;
    private String name;
    private String address;
    private String status;
    private String phoneNumber;
    private String price;

    public BDSModel(){

    }

    public BDSModel(String id, String name, String address, String status, String phoneNumber, String price) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}


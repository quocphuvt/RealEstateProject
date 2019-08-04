package com.example.realestateproject.models;

public class RealEstate {
    private String id;
    private String name;
    private String address;
    private String contactNumber;
    private String description;
    private Double price;
    private Double area;
    private String _idUser;
    private String _idCity;
    private String _idType;
    private String _idStatus;

    public RealEstate() {

    }

    public RealEstate(String name, String address, String contactNumber, String description, Double price, Double area) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.description = description;
        this.price = price;
        this.area = area;

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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String get_idUser() {
        return _idUser;
    }

    public void set_idUser(String _idUser) {
        this._idUser = _idUser;
    }

    public String get_idCity() {
        return _idCity;
    }

    public void set_idCity(String _idCity) {
        this._idCity = _idCity;
    }

    public String get_idType() {
        return _idType;
    }

    public void set_idType(String _idType) {
        this._idType = _idType;
    }

    public String get_idStatus() {
        return _idStatus;
    }

    public void set_idStatus(String _idStatus) {
        this._idStatus = _idStatus;
    }
}

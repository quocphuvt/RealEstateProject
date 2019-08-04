package com.example.realestateproject.models;

public class RealEstate {
    private String _id;
    private String name;
    private String address;
    private String contactNumber;
    private String description;
    private Double price;
    private Double area;
    private String city;
    private String type;
    private String status;
    private String _idUser;

    public RealEstate() {

    }

    public RealEstate(String name, String address, String contactNumber, String description, Double price, Double area, String city, String type, String status, String _idUser) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.description = description;
        this.price = price;
        this.area = area;
        this.city = city;
        this.type = type;
        this.status = status;
        this._idUser = _idUser;
    }

    public RealEstate(String _id, String name, String address, String contactNumber, String description, Double price, Double area, String city, String type, String status, String _idUser) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.description = description;
        this.price = price;
        this.area = area;
        this.city = city;
        this.type = type;
        this.status = status;
        this._idUser = _idUser;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

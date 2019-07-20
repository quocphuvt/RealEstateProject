package com.example.realestateproject.models;

public class UserModel {
    private String id;
    private String password;
    private String fullName;
    private String birthday;
    private String address;
    private String phoneNumber;
    private int gender;

    public UserModel(){

    }

    public UserModel(String id, String password, String fullName, String birthday, String address, String phoneNumber, int gender) {
        this.id = id;
        this.password = password;
        this.fullName = fullName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}

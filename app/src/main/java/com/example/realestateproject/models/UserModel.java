package com.example.realestateproject.models;

public class UserModel {
    private String _id;
    private String password;
    private String fullName;
    private String birthday;
    private String city;
    private String phoneNumber;
    private int gender;
    private String _idFavorite;
    private String _idReal;

    public UserModel(String _id, String password, String fullName, String birthday, String city, String phoneNumber, int gender) {
        this._id = _id;
        this.password = password;
        this.fullName = fullName;
        this.birthday = birthday;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String get_idFavorite() {
        return _idFavorite;
    }

    public void set_idFavorite(String _idFavorite) {
        this._idFavorite = _idFavorite;
    }

    public String get_idReal() {
        return _idReal;
    }

    public void set_idReal(String _idReal) {
        this._idReal = _idReal;
    }
}

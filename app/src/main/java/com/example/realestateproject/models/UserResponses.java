package com.example.realestateproject.models;

import java.util.List;

public class UserResponses {
    private int status;
    private String message;
    private UserModel userModel;
    private List<UserModel> userList;
    private RealEstate realEstate;
    private List<RealEstate> realList;
    private Favorites favorites;
    private List<Favorites> favoritesList;

    public UserResponses(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public UserResponses(int status, String message, UserModel userModel, List<UserModel> userList, RealEstate realEstate, List<RealEstate> realList, Favorites favorites, List<Favorites> favoritesList) {
        this.status = status;
        this.message = message;
        this.userModel = userModel;
        this.userList = userList;
        this.realEstate = realEstate;
        this.realList = realList;
        this.favorites = favorites;
        this.favoritesList = favoritesList;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }

    public RealEstate getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(RealEstate realEstate) {
        this.realEstate = realEstate;
    }

    public List<RealEstate> getRealList() {
        return realList;
    }

    public void setRealList(List<RealEstate> realList) {
        this.realList = realList;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public List<Favorites> getFavoritesList() {
        return favoritesList;
    }

    public void setFavoritesList(List<Favorites> favoritesList) {
        this.favoritesList = favoritesList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

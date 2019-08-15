package com.example.realestateproject.models;

import java.util.List;

public class Favorites {
    private String _idUser;
    private List<FavoritedReal> favoritedReals;

    public Favorites(String _idUser, List<FavoritedReal> favoritedReals) {
        this._idUser = _idUser;
        this.favoritedReals = favoritedReals;
    }

    public String get_idUser() {
        return _idUser;
    }

    public void set_idUser(String _idUser) {
        this._idUser = _idUser;
    }

    public List<FavoritedReal> getFavoritedReals() {
        return favoritedReals;
    }

    public void setFavoritedReals(List<FavoritedReal> favoritedReals) {
        this.favoritedReals = favoritedReals;
    }
}

package com.example.realestateproject.models;

public class FavoritedReal {
    private String objectId;
    private String _idReal;
    private boolean isLike;

    public FavoritedReal(String _idReal, boolean isLike) {
        this._idReal = _idReal;
        this.isLike = isLike;
    }

    public String get_idReal() {
        return _idReal;
    }

    public void set_idReal(String _idReal) {
        this._idReal = _idReal;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}

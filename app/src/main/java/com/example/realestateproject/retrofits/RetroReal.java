package com.example.realestateproject.retrofits;

import com.example.realestateproject.models.RealEstate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetroReal {
    @POST("/real_creating")
    Call<RealEstate> createReal(@Body RealEstate realEstate);

    @POST("/list_real")
    Call<List<RealEstate>> getListreals();

}


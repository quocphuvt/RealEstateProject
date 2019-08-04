package com.example.realestateproject.retrofits;

import com.example.realestateproject.models.RealEstate;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroReal {
    @POST("/real_creating")
    Call<RealEstate> createReal(@Body RealEstate realEstate);

    @POST("/list_real")
    Call<List<RealEstate>> getListreals();

    @POST("/list_real_for_lease")
    Call<List<RealEstate>> getAllRealsForLease();

    @POST("/list_real_for_sale")
    Call<List<RealEstate>> getAllRealsForSale();

    @POST("/real_by_id")
    @FormUrlEncoded
    Observable<RealEstate> getRealById(@Field("id") String id);

}


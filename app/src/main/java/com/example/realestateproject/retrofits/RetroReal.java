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

    @POST("/real_by_location")
    @FormUrlEncoded
    Observable<RealEstate> getRealByLocation(@Field("location") String location);

    @POST("/num_real")
    @FormUrlEncoded
    Observable<Integer> countNumReal(@Field("id") String id);

    @POST("/sort_price")
    Call<List<RealEstate>> sortByPrice();

    @POST("/filter_price")
    @FormUrlEncoded
    Observable<List<RealEstate>> filterRealByPrice(@Field("minPrice") double minPrice, @Field("maxPrice") double maxPrice);

    @POST("/history")
    @FormUrlEncoded
    Observable<List<RealEstate>> getHistoryByIdUser(@Field("id") String idUser);

    @POST("/get_available_reals")
    Call<List<RealEstate>> getAvailableReals();

    @POST("/delete_real_by_id")
    @FormUrlEncoded
    Observable<Integer> deleteRealById(@Field("id") String idReal);

    @POST("/update_real")
    Call<Integer> updateReal(@Body RealEstate realEstate);
}


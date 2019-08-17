package com.example.realestateproject.retrofits;

import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.models.UserResponses;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroReal {
    @POST("/real/create")
    Call<UserResponses> createReal(@Body RealEstate realEstate);

    @GET("/real/realList/all")
    Call<UserResponses> getAllReals();

    @GET("/real/realList/sort")
    Call<UserResponses> sortByPrice();

    @GET("/real/realList/filter")
    Call<UserResponses> filterRealByPrice(@Query("minPrice") int minPrice,
                                          @Query("maxPrice") int maxPrice);

    @GET("/real/realList/lease")
    Call<UserResponses> getAllRealsForLease();

    @GET("/real/realList/sale")
    Call<UserResponses> getAllRealsForSale();

    @GET("/real/{city}/realList")
    Call<UserResponses> getRealListByCity(@Path("city") String city);

    @GET("/real/{id}")
    Call<UserResponses> getRealById(@Path("id") String realId);

    @GET("/real/location/{location}")
    Call<UserResponses> getRealByLocation(@Path("location") String location);

    @GET("/real/{userId}/totalReals")
    Call<UserResponses> countNumReal(@Path("userId") String idUser);

    @GET("/real/{userId}/history")
    Call<UserResponses> getHistoryByIdUser(@Path("userId") String idUser);

    @GET("/real/{userId}/realList/available")
    Call<UserResponses> getAvailableReals(@Path("userId") String idUser);

    @DELETE("/real/{realId}")
    Call<UserResponses> deleteRealById(@Path("realId") String realId);

    @PUT("/real")
    Call<UserResponses> updateReal(@Body RealEstate realEstate);
}


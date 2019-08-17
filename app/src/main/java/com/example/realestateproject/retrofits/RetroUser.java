package com.example.realestateproject.retrofits;

import com.example.realestateproject.models.Favorites;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.models.UserResponses;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroUser {
    @POST("/user/register")
    Call<UserResponses> registerUser(@Body UserModel userModel);

    @GET("/user/signIn")
    Call<UserResponses> checkUserLogin(@Query("id") String id,
                                       @Query("password") String password);

    @GET("/user/{id}")
    Call<UserResponses> getCurrentUser(@Path("id") String id);

    @PUT("/user")
    Call<UserResponses> updateUserData(@Body UserModel userModel);

    @POST("/user/saveFavorite")
    Call<Favorites> setFavoritedReal(@Body Favorites favorites);

<<<<<<< HEAD
    @POST("get_user")
    @FormUrlEncoded
    Observable<UserModel> getCurrentUser(@Field("id") String id);

    @POST("/update_user")
    Call<UserModel> updateUser(@Body UserModel userModel);

}
=======
    @GET("/user/favoriteList")
    Call<Favorites> getFavoritedReals();
 }
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b

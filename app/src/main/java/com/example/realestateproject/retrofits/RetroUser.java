package com.example.realestateproject.retrofits;

import com.example.realestateproject.models.UserModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroUser {
    @POST("register")
    Call<UserModel> registerUser(@Body UserModel userModel);

    @POST("sign_in")
    @FormUrlEncoded
    Call<String> checkUserLogin(@Field("id") String id, @Field("password") String password);

}

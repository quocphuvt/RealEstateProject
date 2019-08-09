package com.example.realestateproject.retrofits;

import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.models.UserResponses;

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
    Observable<UserResponses> checkUserLogin(@Field("id") String id, @Field("password") String password);

    @POST("get_user")
    @FormUrlEncoded
    Observable<UserModel> getCurrentUser(@Field("id") String id);

    @POST("update_user")
    Call<UserModel> updateUserData(@Body UserModel userModel);
}

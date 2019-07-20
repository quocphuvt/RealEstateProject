package com.example.realestateproject.retrofits;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroUser {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("id") String id, @Field("password") String password);

    @POST("sign_in")
    @FormUrlEncoded
    Observable<String> checkUserLogin(@Field("id") String id, @Field("password") String password);

}

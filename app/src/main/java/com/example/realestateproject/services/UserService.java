//package com.example.realestateproject.services;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.widget.Toast;
//
//import com.example.realestateproject.activities.HomeActivity;
//import com.example.realestateproject.activities.SignInActivity;
//import com.example.realestateproject.models.UserModel;
//import com.example.realestateproject.models.UserResponses;
//import com.example.realestateproject.retrofits.RetroClient;
//import com.example.realestateproject.retrofits.RetroUser;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//import retrofit2.Retrofit;
//
//public abstract class UserService {
//    private RetroUser retroUser;
//    protected void getCurrentUser(String id){
//        final UserModel userModel;
//        Retrofit retrofitClient = RetroClient.getInstance();
//        retroUser = retrofitClient.create(RetroUser.class);
//        retroUser.getCurrentUser(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<UserModel>() {
//                    @Override
//                    public void accept(UserModel userModel) throws Exception {
//
//                    }
//                });
//    }
//}

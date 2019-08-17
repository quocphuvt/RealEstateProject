package com.example.realestateproject.activities;

import android.os.Bundle;

import com.example.realestateproject.adapters.MyRealEstatesAdapter;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.ListView;

import com.example.realestateproject.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyRealEstatesActivity extends AppCompatActivity{
    private ListView lv_myRealEstates;
    private RetroReal retroReal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_real_estates);
        Toolbar toolbar = findViewById(R.id.toolbar);
        lv_myRealEstates = findViewById(R.id.lv_myReals);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All real was posting");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Retrofit retrofit = RetroClient.getInstance();
        retroReal = retrofit.create(RetroReal.class);
        getAvailableReals();
    }

    private void getAvailableReals() {
        Call<UserResponses> call = retroReal.getAvailableReals();
        call.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if(response.isSuccessful()){
                    UserResponses userResponses = response.body();
                    if( userResponses.getStatus() == 1) {
                        MyRealEstatesAdapter myRealEstatesAdapter = new MyRealEstatesAdapter(userResponses.getRealList(), MyRealEstatesActivity.this);
                        lv_myRealEstates.setAdapter(myRealEstatesAdapter);
                    }
                    else return;
                }
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

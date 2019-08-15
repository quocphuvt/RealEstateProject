package com.example.realestateproject.activities;

import android.os.Bundle;

import com.example.realestateproject.adapters.MyRealEstatesAdapter;
import com.example.realestateproject.models.RealEstate;
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
        RetroReal retroReal = retrofit.create(RetroReal.class);
        getAvailableReals(retroReal);
    }

    private void getAvailableReals(RetroReal retroReal) {
        Call<List<RealEstate>> call = retroReal.getAvailableReals();
        call.enqueue(new Callback<List<RealEstate>>() {
            @Override
            public void onResponse(Call<List<RealEstate>> call, Response<List<RealEstate>> response) {
                if(response.isSuccessful()){
                    MyRealEstatesAdapter myRealEstatesAdapter = new MyRealEstatesAdapter(response.body(), MyRealEstatesActivity.this);
                    lv_myRealEstates.setAdapter(myRealEstatesAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<RealEstate>> call, Throwable t) {

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

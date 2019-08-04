package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.adapters.ListRealAdapter;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListRealActivity extends AppCompatActivity {
    private ListView lv_reals;
    private RetroReal retroReal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_real);
        Retrofit retrofit = RetroClient.getInstance();
        retroReal = retrofit.create(RetroReal.class);
        Call<List<RealEstate>> call = retroReal.getListreals();
        call.enqueue(new Callback<List<RealEstate>>() {
            @Override
            public void onResponse(Call<List<RealEstate>> call, Response<List<RealEstate>> response) {
                Toast.makeText(ListRealActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                ListRealAdapter listRealAdapter = new ListRealAdapter(response.body(),ListRealActivity.this);
                lv_reals.setAdapter(listRealAdapter);

            }

            @Override
            public void onFailure(Call<List<RealEstate>> call, Throwable t) {

            }
        });

        lv_reals=findViewById(R.id.lv_reals_listreal);
    }
}

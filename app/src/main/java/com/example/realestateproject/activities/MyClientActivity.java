package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.realestateproject.R;
import com.example.realestateproject.adapters.ListRealAdapter;
import com.example.realestateproject.interfaces.ClickRealItemListener;
import com.example.realestateproject.models.FavoritedReal;
import com.example.realestateproject.models.Favorites;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.retrofits.RetroUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyClientActivity extends AppCompatActivity implements ClickRealItemListener {
    private Toolbar toolbar;
    private ListView lv_favorite;
    private Retrofit retrofit;
    private RetroUser retroUser;
    private RetroReal retroReal;
    private Favorites favorites = null;
    private List<RealEstate> realEstates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_client);
        toolbar = findViewById(R.id.toolbar);
        lv_favorite = findViewById(R.id.lv_favorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        retrofit = RetroClient.getInstance();
        retroUser = retrofit.create(RetroUser.class);
        retroReal = retrofit.create(RetroReal.class);
        Call<Favorites> callFavoritedRealsGetting = retroUser.getFavoritedReals();
        callFavoritedRealsGetting.enqueue(new Callback<Favorites>() {
            @Override
            public void onResponse(Call<Favorites> call, Response<Favorites> response) {
                favorites = response.body();
                if (response.body() != null) {
                    realEstates = new ArrayList<>();
                    for (int i = 0; i < favorites.getFavoritedReals().size(); i++) {
                        FavoritedReal favoritedReal = favorites.getFavoritedReals().get(i);
                        retroReal.getRealById(favoritedReal.get_idReal())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<RealEstate>() {
                                    @Override
                                    public void accept(RealEstate realEstate) throws Exception {
                                        realEstates.add(realEstate);
                                    }
                                });
                    }
                        ListRealAdapter listRealAdapter = new ListRealAdapter(realEstates, MyClientActivity.this, MyClientActivity.this);
                        lv_favorite.setAdapter(listRealAdapter);
                }
            }

            @Override
            public void onFailure(Call<Favorites> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(String idReal) {
        Intent i = new Intent(this, RealDetailsActivity.class);
        i.putExtra("id", idReal);
        startActivity(i);
    }
}

package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.realestateproject.R;
import com.example.realestateproject.adapters.ListRealAdapter;
import com.example.realestateproject.interfaces.ClickRealItemListener;
import com.example.realestateproject.models.FavoritedReal;
import com.example.realestateproject.models.Favorites;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.models.UserResponses;
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
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String idUser = sharedPreferences.getString("id", "");
        retrofit = RetroClient.getInstance();
        retroUser = retrofit.create(RetroUser.class);
        retroReal = retrofit.create(RetroReal.class);
        Call<UserResponses> callFavoritedRealsGetting = retroUser.getFavoritedReals(idUser);
        callFavoritedRealsGetting.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if (response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    favorites = userResponses.getFavorites();
                    if (userResponses.getStatus() == 1) {
                        realEstates = new ArrayList<>();
                        for (int i = 0; i < favorites.getFavoritedReals().size(); i++) {
                            FavoritedReal favoritedReal = favorites.getFavoritedReals().get(i);
                            if(favoritedReal.isLike()) {
                                Call<UserResponses> callFavorite = retroReal.getRealById(favoritedReal.get_idReal());
                                callFavorite.enqueue(new Callback<UserResponses>() {
                                    @Override
                                    public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                                        if (response.isSuccessful()) {
                                            UserResponses userResponses = response.body();
                                            if (userResponses.getStatus() == 1) {
                                                realEstates.add(userResponses.getRealEstate());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserResponses> call, Throwable t) {

                                    }
                                });
                            }
                        }
                        ListRealAdapter listRealAdapter = new ListRealAdapter(realEstates, MyClientActivity.this, MyClientActivity.this);
                        lv_favorite.setAdapter(listRealAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

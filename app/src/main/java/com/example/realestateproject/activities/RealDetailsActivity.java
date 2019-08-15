package com.example.realestateproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.realestateproject.R;
import com.example.realestateproject.models.FavoritedReal;
import com.example.realestateproject.models.Favorites;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RealDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_name, tv_address, tv_type, tv_description, tv_area, tv_price, tv_contact;
    private Button btn_selected, btn_seeUsOnMap;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private ImageView iv_realImg;
    private LinearLayout layout_makeCall;
    private String phoneNumber;
    private ImageView iv_favorite;
    private boolean isLike = false;
    private String idReal;
    private String idUser;
    private Retrofit retrofit;
    private RetroUser retroUser;
    private List<FavoritedReal> favoritedReals = new ArrayList<>();
    private Favorites favorites = null;

    private void initView() {
        tv_name = findViewById(R.id.tv_name_realDetail);
        tv_address = findViewById(R.id.tv_address_realDetail);
        tv_type = findViewById(R.id.tv_type_realDetail);
        tv_description = findViewById(R.id.tv_description_realDetail);
        btn_selected = findViewById(R.id.btn_selected_realDetail);
        ratingBar = findViewById(R.id.ratingBar);
        tv_area = findViewById(R.id.tv_area_realDetail);
        tv_price = findViewById(R.id.tv_price_realDetail);
        tv_contact = findViewById(R.id.tv_contact_realDetail);
        toolbar = findViewById(R.id.toolbar_realDetail);
        btn_seeUsOnMap = findViewById(R.id.btn_seeUsOnMap);
        iv_realImg = findViewById(R.id.iv_realImg_detail);
        layout_makeCall = findViewById(R.id.layout_makeCall);
        iv_favorite = findViewById(R.id.iv_favorite);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_details);
        this.initView();
        retrofit = RetroClient.getInstance();
        retroUser = retrofit.create(RetroUser.class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        btn_selected.setOnClickListener(this);
        layout_makeCall.setOnClickListener(this);
        iv_favorite.setOnClickListener(this);
        ratingBar.setRating(3);
        Intent i = getIntent();
        idReal = i.getStringExtra("id");
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        idUser = sharedPreferences.getString("id", "");
        this.getFavoritedReals();
        RetroReal retroReal = retrofit.create(RetroReal.class);
        retroReal.getRealById(idReal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RealEstate>() {
                    @Override
                    public void accept(RealEstate realEstate) throws Exception {
                        getSupportActionBar().setTitle(realEstate.getCity());
                        tv_name.setText(realEstate.getName());
                        tv_address.setText(realEstate.getAddress());
                        tv_type.setText(realEstate.getType());
                        tv_description.setText(realEstate.getDescription());
                        tv_area.setText(String.format("%.0f", realEstate.getArea()) + " m2");
                        tv_price.setText("$ " + String.format("%.0f", realEstate.getPrice()));
                        tv_contact.setText(realEstate.getContactNumber());
                        iv_realImg.setImageBitmap(Utils.decodeBase64Image(realEstate.getImg()));
                        phoneNumber = realEstate.getContactNumber();
                    }
                });
    }

    private void getFavoritedReals() {
        Call<Favorites> callFavoritedRealsGetting = retroUser.getFavoritedReals();
        callFavoritedRealsGetting.enqueue(new Callback<Favorites>() {
            @Override
            public void onResponse(Call<Favorites> call, Response<Favorites> response) {
                if (response.body() != null) {
                    favorites = response.body();
                    for (int i = 0; i < favorites.getFavoritedReals().size(); i++) {
                        FavoritedReal favoritedReal = favorites.getFavoritedReals().get(i);
                        if (favoritedReal.get_idReal().equals(idReal)) {
                            isLike = favoritedReal.isLike();
                            iv_favorite.setImageResource(favoritedReal.isLike() ? R.drawable.ic_favorite_active : R.drawable.ic_favorite_unactive);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Favorites> call, Throwable t) {

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_selected_realDetail:
                break;
            case R.id.btn_seeUsOnMap:
                break;
            case R.id.layout_makeCall:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
                startActivity(intent);
                break;
            case R.id.iv_favorite:
                if (isLike) {
                    iv_favorite.setImageResource(R.drawable.ic_favorite_unactive);
                    isLike = false;
                } else {
                    iv_favorite.setImageResource(R.drawable.ic_favorite_active);
                    isLike = true;
                }
        }
    }

    //TODO: WIP
    @Override
    public void finish() {
        super.finish();
        if (favorites != null) {
            boolean isNotExist = false;
            for (int i = 0; i < favorites.getFavoritedReals().size(); i++) {
                FavoritedReal favoritedReal = favorites.getFavoritedReals().get(i);
                if (favoritedReal.get_idReal().equals(idReal)) {
                    favorites.getFavoritedReals().set(i, new FavoritedReal(idReal, isLike));
                } else {
                    if (i == favorites.getFavoritedReals().size() - 1) {
                        favorites.getFavoritedReals().add(new FavoritedReal(idReal, isLike));
                        break;
                    }
                }
            }
        } else {
            favoritedReals.add(new FavoritedReal(idReal, isLike)); //Use for get first time
            favorites = new Favorites(idUser, favoritedReals);
        }
        Call<Favorites> call = retroUser.setFavoritedReal(favorites);
        call.enqueue(new Callback<Favorites>() {
            @Override
            public void onResponse(Call<Favorites> call, Response<Favorites> response) {
                if (response.isSuccessful()) {
                    Log.i("favorite", response.body().getFavoritedReals().toString());
                }
            }

            @Override
            public void onFailure(Call<Favorites> call, Throwable t) {

            }
        });
    }
}

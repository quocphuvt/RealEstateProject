package com.example.realestateproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.example.realestateproject.R;
import com.example.realestateproject.adapters.ListRealAdapter;
import com.example.realestateproject.interfaces.ClickRealItemListener;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.supports.Constants;
import com.example.realestateproject.supports.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListRealActivity extends AppCompatActivity implements ClickRealItemListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ListView lv_reals;
    private Toolbar toolbar;
    private RetroReal retroReal;
    private LinearLayout sort_layout, filter_layout;

    private void initView() {
        lv_reals = findViewById(R.id.lv_reals_listreal);
        toolbar = findViewById(R.id.toolbar);
        sort_layout = findViewById(R.id.sort_layout);
        filter_layout = findViewById(R.id.filter_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_real);
        this.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        Intent i = getIntent();
        final String city = i.getStringExtra("city");
        filter_layout.setOnClickListener(this);
        sort_layout.setOnClickListener(this);
        Retrofit retrofit = RetroClient.getInstance();
        retroReal = retrofit.create(RetroReal.class);
        //TODO: SHOW ALL REALS
        Call<List<RealEstate>> call = retroReal.getListreals();
        call.enqueue(new Callback<List<RealEstate>>() {
            @Override
            public void onResponse(Call<List<RealEstate>> call, Response<List<RealEstate>> response) {
                String property = response.body().size() < 2 ? " property" : " properties";
                if (TextUtils.isEmpty(city)) {
                    getSupportActionBar().setTitle("Have " + response.body().size()+property);
                } else  getSupportActionBar().setTitle(city + "have " + response.body().size()+property);
                ListRealAdapter listRealAdapter = new ListRealAdapter(response.body(), ListRealActivity.this, ListRealActivity.this);
                lv_reals.setAdapter(listRealAdapter);

            }

            @Override
            public void onFailure(Call<List<RealEstate>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickItem(String idReal) {
        Toast.makeText(this, "" + idReal, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, RealDetailsActivity.class);
        i.putExtra("id", idReal);
        startActivity(i);
    }


    @Override
    public void onClick(View view) {
        //TODO: SORT BY PRICE
        switch (view.getId()) {
            case R.id.filter_layout:
                showPopup(findViewById(R.id.filter_layout));
                break;
            case R.id.sort_layout:
                RealEstate realEstate = new RealEstate();
                Call<List<RealEstate>> call = retroReal.sortByPrice();
                call.enqueue(new Callback<List<RealEstate>>() {
                    @Override
                    public void onResponse(Call<List<RealEstate>> call, Response<List<RealEstate>> response) {
                        ListRealAdapter listRealAdapter = new ListRealAdapter(response.body(), ListRealActivity.this, ListRealActivity.this);
                        lv_reals.setAdapter(listRealAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<RealEstate>> call, Throwable t) {

                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                String price = Constants.RANGE_OF_PRICE[0];
                int minPrice = getMinAndMaxPrice(price, 0);
                int maxPrice = getMinAndMaxPrice(price, 1);
                Log.i("min", minPrice + "");
                Log.i("max", maxPrice + "");
                Retrofit retrofit = RetroClient.getInstance();
                RetroReal retroReal = retrofit.create(RetroReal.class);
                retroReal.filterRealByPrice(minPrice, maxPrice)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<RealEstate>>() {
                            @Override
                            public void accept(List<RealEstate> realEstates) throws Exception {
                                ListRealAdapter listRealAdapter = new ListRealAdapter(realEstates, ListRealActivity.this, ListRealActivity.this);
                                lv_reals.setAdapter(listRealAdapter);
                            }
                        });
                return true;
            case R.id.option2:
                String price2 = Constants.RANGE_OF_PRICE[1];
                int minPrice2 = getMinAndMaxPrice(price2, 0);
                int maxPrice2 = getMinAndMaxPrice(price2, 1);
                Retrofit retrofit2 = RetroClient.getInstance();
                RetroReal retroReal2 = retrofit2.create(RetroReal.class);
                retroReal2.filterRealByPrice(minPrice2, maxPrice2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<RealEstate>>() {
                            @Override
                            public void accept(List<RealEstate> realEstates) throws Exception {
                                ListRealAdapter listRealAdapter = new ListRealAdapter(realEstates, ListRealActivity.this, ListRealActivity.this);
                                lv_reals.setAdapter(listRealAdapter);
                            }
                        });
                return true;
            default:
                return false;
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(this);
    }

    private int getMinAndMaxPrice(String prices, int i) {
        if (i == 0) { //Min
            String minPrice = prices.substring(0, prices.indexOf(','));
            Log.i("min", minPrice);
            return Integer.parseInt(minPrice);
        } else {
            String maxPrice = prices.substring(prices.indexOf(',') + 1, prices.length());
            Log.i("max", maxPrice);
            return Integer.parseInt(maxPrice);
        }
    }
}
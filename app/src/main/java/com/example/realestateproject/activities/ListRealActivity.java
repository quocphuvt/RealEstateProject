package com.example.realestateproject.activities;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
=======
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
<<<<<<< HEAD
import android.view.View;
import android.widget.Button;
=======
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
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
import com.example.realestateproject.models.UserResponses;
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

<<<<<<< HEAD
public class ListRealActivity extends AppCompatActivity implements ClickRealItemListener, View.OnClickListener {
=======
public class ListRealActivity extends AppCompatActivity implements ClickRealItemListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
    private ListView lv_reals;
    private Button btn_sort;
    private Toolbar toolbar;
    private RetroReal retroReal;
    private LinearLayout sort_layout, filter_layout;
    private List<RealEstate> realList;

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
<<<<<<< HEAD
        lv_reals = findViewById(R.id.lv_reals_listreal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_sort =findViewById(R.id.btn_sortPirce);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        Intent i = getIntent();
        final String city = i.getStringExtra("city");
        btn_sort.setOnClickListener(this);
        Retrofit retrofit = RetroClient.getInstance();
        retroReal = retrofit.create(RetroReal.class);
        Call<List<RealEstate>> call = retroReal.getListreals();
//        call.enqueue(new Callback<List<RealEstate>>() {
//            @Override
//            public void onResponse(Call<List<RealEstate>> call, Response<List<RealEstate>> response) {
//                getSupportActionBar().setTitle(city+" "+response.body().size());
////                Toast.makeText(ListRealActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
//                ListRealAdapter listRealAdapter = new ListRealAdapter(response.body(),ListRealActivity.this, ListRealActivity.this);
//                lv_reals.setAdapter(listRealAdapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<RealEstate>> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void onClickItem(String idReal) {
        Toast.makeText(this, ""+idReal, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, RealDetailsActivity.class);
        i.putExtra("id", idReal);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        RealEstate realEstate = new RealEstate();
        Call<List<RealEstate>> call = retroReal.sortByPrice();
        call.enqueue(new Callback<List<RealEstate>>() {
            @Override
            public void onResponse(Call<List<RealEstate>> call, Response<List<RealEstate>> response) {
                ListRealAdapter listRealAdapter = new ListRealAdapter(response.body(),ListRealActivity.this,ListRealActivity.this);
                lv_reals.setAdapter(listRealAdapter);
=======
        this.initView();
        this.setToolbar();
        Retrofit retrofit = RetroClient.getInstance();
        retroReal = retrofit.create(RetroReal.class);
        Intent i = getIntent();
        final String city = i.getStringExtra("city");

        if (TextUtils.isEmpty(city)) {
            this.getAllReals();
        } else {
            this.getRealListByCity(city);
        }

        filter_layout.setOnClickListener(this);
        sort_layout.setOnClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                String price = Constants.RANGE_OF_PRICE[0];
                int minPrice = getMinAndMaxPrice(price, 0);
                int maxPrice = getMinAndMaxPrice(price, 1);
                this.filterByRangeOfPrice(minPrice, maxPrice);
                return true;
            case R.id.option2:
                String price2 = Constants.RANGE_OF_PRICE[1];
                int minPrice2 = getMinAndMaxPrice(price2, 0);
                int maxPrice2 = getMinAndMaxPrice(price2, 1);
                this.filterByRangeOfPrice(minPrice2, maxPrice2);
                return true;
            default:
                return false;
        }
    }

    private void getAllReals() {
        Call<UserResponses> call = retroReal.getAllReals();
        call.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if (response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if (userResponses.getStatus() == 1) {
                        String property = userResponses.getRealList().size() < 2 ? " property" : " properties";
                        getSupportActionBar().setTitle("Have " + userResponses.getRealList().size() + property);
                        realList = userResponses.getRealList();
                        setRealListAdapter(realList);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }

    private void getRealListByCity(String cityName) {
        Call<UserResponses> callRealListByCity = retroReal.getRealListByCity(cityName);
        callRealListByCity.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if (response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if (userResponses.getStatus() == 1) {
                        String property = userResponses.getRealList().size() < 2 ? " property" : " properties";
                        getSupportActionBar().setTitle(cityName + " have " + userResponses.getRealList().size() + property);
                        realList = userResponses.getRealList();
                        setRealListAdapter(realList);
                    }
                }
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }
<<<<<<< HEAD
}
=======

    private void sortRealListByPrice() {
        Call<UserResponses> callSort = retroReal.sortByPrice();
        callSort.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if(response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if(userResponses.getStatus() == 1) {
                        realList = userResponses.getRealList();
                        setRealListAdapter(realList);
                    }
                    else return;
                }
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }

    private void filterByRangeOfPrice(int minPrice, int maxPrice) {
        Call<UserResponses> callFilteredReals = retroReal.filterRealByPrice(minPrice, maxPrice);
        callFilteredReals.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if(response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if(userResponses.getStatus() == 1) {
                        setRealListAdapter(userResponses.getRealList());
                    }
                    else return;
                }
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }

    private void setRealListAdapter(List<RealEstate> realList) {
        ListRealAdapter listRealAdapter = new ListRealAdapter(realList, ListRealActivity.this, ListRealActivity.this);
        lv_reals.setAdapter(listRealAdapter);
    }

    private int getMinAndMaxPrice(String prices, int i) {
        if (i == 0) { //Min
            String minPrice = prices.substring(0, prices.indexOf(','));
            return Integer.parseInt(minPrice);
        } else {
            String maxPrice = prices.substring(prices.indexOf(',') + 1, prices.length());
            return Integer.parseInt(maxPrice);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_layout:
                showPopup(findViewById(R.id.filter_layout));
                break;
            case R.id.sort_layout:
                this.sortRealListByPrice();
                break;
        }
    }

    @Override
    public void onClickItem(String idReal) {
        Intent i = new Intent(this, RealDetailsActivity.class);
        i.putExtra("id", idReal);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
    }
}
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b

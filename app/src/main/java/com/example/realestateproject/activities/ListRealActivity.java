package com.example.realestateproject.activities;

import android.content.Intent;
import android.os.Bundle;
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
    private Button btn_sort;
    private Toolbar toolbar;
    private RetroReal retroReal;
    private Spinner sp_filter;
    private LinearLayout sort_layout, filter_layout;
    private List<RealEstate> realEstateList;
    private List<RealEstate> sortResult;

    private void initView() {
        lv_reals = findViewById(R.id.lv_reals_listreal);
        toolbar = findViewById(R.id.toolbar);
        sort_layout = findViewById(R.id.sort_layout);
        filter_layout = findViewById(R.id.filter_layout);
//        btn_sort = findViewById(R.id.btn_sortPirce);
//        sp_filter = findViewById(R.id.sp_filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_real);
        this.initView();
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        Intent i = getIntent();
        final String city = i.getStringExtra("city");
//        btn_sort.setOnClickListener(this);
        realEstateList = new ArrayList<>();
        filter_layout.setOnClickListener(this);
        sort_layout.setOnClickListener(this);
        //TODO: SPINNER SHOW REALS
//        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Constants.RANGE_OF_PRICE);
//        sp_filter.setAdapter(filterAdapter);
//        sp_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(ListRealActivity.this, ""+adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
//                int minPrice = getMinAndMaxPrice(adapterView.getItemAtPosition(i).toString(), 0);
//                int maxPrice = getMinAndMaxPrice(adapterView.getItemAtPosition(i).toString(), 1);
//                Retrofit retrofit = RetroClient.getInstance();
//                RetroReal retroReal = retrofit.create(RetroReal.class);
//                retroReal.filterRealByPrice(minPrice, maxPrice)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<List<RealEstate>>() {
//                            @Override
//                            public void accept(List<RealEstate> realEstates) throws Exception {
//                                for(RealEstate model: realEstates){
//                                    realEstateList.add(model);
//                                }
//                                ListRealAdapter listRealAdapter = new ListRealAdapter(realEstates, ListRealActivity.this, ListRealActivity.this);
//                                lv_reals.setAdapter(listRealAdapter);
//                            }
//                        });
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        Retrofit retrofit = RetroClient.getInstance();
        retroReal = retrofit.create(RetroReal.class);
        //TODO: SHOW ALL REALS
//        Call<List<RealEstate>> call = retroReal.getListreals();
//        call.enqueue(new Callback<List<RealEstate>>() {
//            @Override
//            public void onResponse(Call<List<RealEstate>> call, Response<List<RealEstate>> response) {
////                getSupportActionBar().setTitle(city+" "+response.body().size());
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "click menu ne", Toast.LENGTH_SHORT).show();
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
                                for (RealEstate model : realEstates) {
                                    realEstateList.add(model);
                                }
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
                                for (RealEstate model : realEstates) {
                                    realEstateList.add(model);
                                }
                                ListRealAdapter listRealAdapter = new ListRealAdapter(realEstates, ListRealActivity.this, ListRealActivity.this);
                                lv_reals.setAdapter(listRealAdapter);
                            }
                        });
                return true;
            default:
                return false;
        }
    }
}
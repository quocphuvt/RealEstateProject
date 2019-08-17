package com.example.realestateproject.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.realestateproject.R;
import com.example.realestateproject.activities.ListRealActivity;
import com.example.realestateproject.activities.RealDetailsActivity;
import com.example.realestateproject.adapters.RealsForLeaseAdapter;
import com.example.realestateproject.adapters.RealsForSaleAdapter;
import com.example.realestateproject.interfaces.ClickRealItemListener;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.supports.Constants;
import com.example.realestateproject.supports.RecyclerViewMargin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements ClickRealItemListener {
    private RecyclerView rv_lease, rv_sale;
    private AppCompatAutoCompleteTextView sv_location;
    private ImageView iv_showRealInCity;
    private RealsForLeaseAdapter realsForLeaseAdapter;
    private RealsForSaleAdapter realsForSaleAdapter;
    private RetroReal retroReal;
    private SwipeRefreshLayout swipeRefreshLayout;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rv_lease = view.findViewById(R.id.rv_lease_search);
        rv_sale = view.findViewById(R.id.rv_sale_search);
        sv_location = view.findViewById(R.id.sv_location_search);
        iv_showRealInCity = view.findViewById(R.id.iv_showRealInCity_search);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        final RecyclerViewMargin decoration = new RecyclerViewMargin(20, 1);
        rv_lease.addItemDecoration(decoration);
        rv_sale.addItemDecoration(decoration);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Constants.CITIES);
        sv_location.setThreshold(1);
        sv_location.setAdapter(cityAdapter);
        sv_location.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                if (Constants.CITIES.length > 0) {
                    if (!sv_location.getText().toString().equals(""))
                        cityAdapter.getFilter().filter(null);
                    sv_location.showDropDown();
                }
                return false;
            }
        });

        iv_showRealInCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ListRealActivity.class);
                i.putExtra("city", sv_location.getText().toString());
                startActivity(i);
            }
        });
        this.refreshData();
        return view;
    }

    private void refreshData(){
        Retrofit retrofit = RetroClient.getInstance();
        retroReal = retrofit.create(RetroReal.class);
        Call<UserResponses> callForLease = retroReal.getAllRealsForLease();
        callForLease.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if(response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if(userResponses.getStatus() == 1) {
                        setRealListAdapter(userResponses.getRealList(), 0);
                    }
                }
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });

        Call<UserResponses> callForSale = retroReal.getAllRealsForSale();
        callForSale.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if(response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if(userResponses.getStatus() == 1) {
                        setRealListAdapter(userResponses.getRealList(), 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }

    private void setRealListAdapter(List<RealEstate> realEstates, int i) {
        if(i == 0) { //LEASE
            rv_lease.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
            realsForLeaseAdapter = new RealsForLeaseAdapter(realEstates, getContext(), SearchFragment.this);
            rv_lease.setAdapter(realsForLeaseAdapter);
        }
        else if(i == 1) { //SALE
            rv_sale.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
            realsForSaleAdapter = new RealsForSaleAdapter(realEstates, getContext(), SearchFragment.this);
            rv_sale.setAdapter(realsForSaleAdapter);
        }
    }

    @Override
    public void onClickItem(String idReal) {
        Intent i = new Intent(getContext(), RealDetailsActivity.class);
        i.putExtra("id", idReal);
        startActivity(i);
    }
}

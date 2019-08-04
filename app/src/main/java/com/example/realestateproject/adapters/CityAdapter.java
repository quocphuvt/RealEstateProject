package com.example.realestateproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.realestateproject.R;
import com.example.realestateproject.supports.LayoutInterface;

import java.util.List;

public class CityAdapter extends BaseAdapter {
    private String[] cities;
    private Context context;

    public CityAdapter(String[] cities, Context context) {
        this.cities = cities;
        this.context = context;
    }


    @Override
    public int getCount() {
        return cities.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_city, null, false);
        TextView tv_cityName = view.findViewById(R.id.tv_city_item);
        tv_cityName.setText(cities[i]);
        return view;
    }
}

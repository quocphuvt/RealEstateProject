package com.example.realestateproject.Adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.realestateproject.R;
import com.example.realestateproject.models.RealEstateModel;

import java.util.List;

public class RealEstateAdapter extends BaseAdapter {
    List<RealEstateModel> list;
    Context c;

    public RealEstateAdapter(List<RealEstateModel> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @Override
    public int getCount() {
        return list.size();
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
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.one_item_realestate, null);
        TextView name = view.findViewById(R.id.tv_name);
        TextView address = view.findViewById(R.id.tv_address);
        TextView contactnumber = view.findViewById(R.id.tv_contact);
        TextView description = view.findViewById(R.id.tv_description);
        TextView price = view.findViewById(R.id.tv_price);
        TextView area = view.findViewById(R.id.tv_area);



        RealEstateModel user = list.get(i);
        name.setText("Name: " + user.getName());
        address.setText("Address: " + user.getAddress());
        contactnumber.setText("ContactNumber: " + user.getContactNumber());
        description.setText("Description: " + user.getDescription());
        price.setText("Price: " + user.getPrice());
        area.setText("Area: " + user.getArea());
        return view;
    }
}



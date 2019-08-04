package com.example.realestateproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.realestateproject.R;
import com.example.realestateproject.models.RealEstate;

import java.util.List;

public class ListRealAdapter extends BaseAdapter {
    private List<RealEstate> listReals;
    private Context context;

    public ListRealAdapter(List<RealEstate> listReals, Context context) {
        this.listReals = listReals;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listReals.size();
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
        view = LayoutInflater.from(context).inflate(R.layout.item_reals,null,false);
        TextView tv_name = view.findViewById(R.id.tv_name_real);
        TextView tv_address = view.findViewById(R.id.tv_address_real);
        TextView tv_area = view.findViewById(R.id.tv_area_real);
        RealEstate realEstate= listReals.get(i);
        tv_name.setText(realEstate.getName());
        tv_address.setText(realEstate.getAddress());
        tv_area.setText(realEstate.getArea().toString());
        return view;
    }
}

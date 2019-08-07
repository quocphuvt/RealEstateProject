package com.example.realestateproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.interfaces.ClickRealItemListener;
import com.example.realestateproject.models.RealEstate;

import java.util.List;

public class ListRealAdapter extends BaseAdapter {
    private List<RealEstate> listReals;
    private Context context;
    private ClickRealItemListener clickRealItemListener;

    public ListRealAdapter(List<RealEstate> listReals, Context context, ClickRealItemListener clickRealItemListener) {
        this.listReals = listReals;
        this.context = context;
        this.clickRealItemListener = clickRealItemListener;
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
        view = LayoutInflater.from(context).inflate(R.layout.item_real_searching_by_city,null,false);
        TextView tv_name = view.findViewById(R.id.tv_name_item_real);
        TextView tv_address = view.findViewById(R.id.tv_address_item_real);
        TextView tv_type = view.findViewById(R.id.tv_type_item_real);
        TextView tv_contact = view.findViewById(R.id.tv_contact_item_real);
        TextView tv_area = view.findViewById(R.id.tv_area_item_real);
        TextView tv_price = view.findViewById(R.id.tv_price_item_real);
        TextView tv_seeDetails = view.findViewById(R.id.tv_seeDetails_item_real);
        final ImageView iv_Favorite = view.findViewById(R.id.iv_Fav);

        final RealEstate realEstate= listReals.get(i);
        tv_name.setText(realEstate.getName());
        tv_address.setText(realEstate.getAddress());
        tv_area.setText("Area: "+String.format("%.0f",realEstate.getArea())+" m");
        tv_type.setText(realEstate.getType());
        tv_contact.setText(realEstate.getContactNumber());
        tv_price.setText("VND "+String.format("%.0f",realEstate.getPrice()));

        tv_seeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Click ne"+realEstate.getId(), Toast.LENGTH_SHORT).show();
                clickRealItemListener.onClickItem(realEstate.getId());
            }
        });

        iv_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iv_Favorite.getDrawable().equals(R.drawable.fav)){
                    iv_Favorite.setImageResource(R.drawable.fav2);
                } else {
                    iv_Favorite.setImageResource(R.drawable.fav);
                }
            }
        });

        return view;
    }
}

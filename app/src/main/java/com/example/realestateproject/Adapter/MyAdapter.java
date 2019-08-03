package com.example.realestateproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.realestateproject.R;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Adapter> {

    List<Adapter> adapterlist;
    Context context;
    int resource;
    public MyAdapter(Context context, int resource, List<Adapter> adapterlist) {
        super(context, resource, adapterlist);
        this.context = context;
        this.resource = resource;
        this.adapterlist = adapterlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        ImageView iv_5 = view.findViewById(R.id.iv_5);
        TextView txtname = view.findViewById(R.id.txtname);
        TextView txtlocate = view.findViewById(R.id.txtlocate);
        TextView txtstatus = view.findViewById(R.id.txtstatus);
        TextView txtphone = view.findViewById(R.id.txtphone);
        TextView txtname1 = view.findViewById(R.id.txtname1);
        TextView txtprice = view.findViewById(R.id.txtprice);

        Adapter adapter = adapterlist.get(position);

        txtname.setText(adapter.getName());
        txtlocate.setText(adapter.getLocate());
        txtstatus.setText(adapter.getStatus ());
        txtphone.setText(adapter.getPhone());
        txtname1.setText(adapter.getName1());
        txtprice.setText(adapter.getPrice());
        return view;
    }
}




package com.example.realestateproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateproject.R;
import com.example.realestateproject.models.RealEstate;

import java.util.List;

public class RealsForLeaseAdapter extends RecyclerView.Adapter<RealsForLeaseViewHolder> {
    private List<RealEstate> realEstates;
    private Context context;

    public RealsForLeaseAdapter(List<RealEstate> realEstates, Context context) {
        this.realEstates = realEstates;
        this.context = context;
    }

    @NonNull
    @Override
    public RealsForLeaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_real_searching, null, false);
        return new RealsForLeaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RealsForLeaseViewHolder holder, int position) {
        RealEstate realEstate = realEstates.get(position);
        holder.tv_name.setText(realEstate.getName());
        holder.tv_city.setText(realEstate.getCity());
    }

    @Override
    public int getItemCount() {
        return realEstates.size();
    }
}
class RealsForLeaseViewHolder extends RecyclerView.ViewHolder {
    protected TextView tv_name, tv_city;
    public RealsForLeaseViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name_item_search);
        tv_city = itemView.findViewById(R.id.tv_city_item_search);
    }
}

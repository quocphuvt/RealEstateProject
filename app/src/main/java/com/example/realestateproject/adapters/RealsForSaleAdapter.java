package com.example.realestateproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateproject.R;
import com.example.realestateproject.interfaces.ClickRealItemListener;
import com.example.realestateproject.models.RealEstate;

import java.util.List;

public class RealsForSaleAdapter extends RecyclerView.Adapter<RealsForLeaseViewHolder> {
    private List<RealEstate> realEstates;
    private Context context;
    private ClickRealItemListener clickRealItemListener;

    public RealsForSaleAdapter(List<RealEstate> realEstates, Context context, ClickRealItemListener clickRealItemListener) {
        this.realEstates = realEstates;
        this.context = context;
        this.clickRealItemListener = clickRealItemListener;
    }

    @NonNull
    @Override
    public RealsForLeaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_real_searching, null, false);
        return new RealsForLeaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RealsForLeaseViewHolder holder, int position) {
        final RealEstate realEstate = realEstates.get(position);
        holder.tv_name.setText(realEstate.getName());
        holder.tv_city.setText(realEstate.getCity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRealItemListener.onClickItem(realEstate.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return realEstates.size();
    }
}
class RealsForSaleViewHolder extends RecyclerView.ViewHolder {
    protected TextView tv_name, tv_city;
    public RealsForSaleViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name_item_search);
        tv_city = itemView.findViewById(R.id.tv_city_item_search);
    }
}

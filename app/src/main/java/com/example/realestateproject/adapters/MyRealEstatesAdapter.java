package com.example.realestateproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.activities.EditRealActivity;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyRealEstatesAdapter extends BaseAdapter {
    private List<RealEstate> realEstates;
    private Context context;

    public MyRealEstatesAdapter(List<RealEstate> realEstates, Context context) {
        this.realEstates = realEstates;
        this.context = context;
    }

    @Override
    public int getCount() {
        return realEstates.size();
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
        view = LayoutInflater.from(context).inflate(R.layout.item_my_real_estate, null, false);
        TextView tv_id = view.findViewById(R.id.tv_id_myReal);
        TextView tv_name = view.findViewById(R.id.tv_name_myReal);
        TextView tv_address = view.findViewById(R.id.tv_address_myReal);
        TextView tv_area = view.findViewById(R.id.tv_area_myReal);
        TextView tv_price = view.findViewById(R.id.tv_price_myReal);
        LinearLayout btn_edit = view.findViewById(R.id.btn_edit_myReal);
        LinearLayout btn_delete = view.findViewById(R.id.btn_delete_myReal);
        RealEstate realEstate = realEstates.get(i);
        tv_id.setText((i + 1)+"");
        tv_name.setText(realEstate.getName());
        tv_address.setText(realEstate.getAddress());
        tv_area.setText(String.format("%.0f", realEstate.getArea())+" m2");
        tv_price.setText(String.format("%.0f", realEstate.getPrice())+" VND");
        Retrofit retrofit = RetroClient.getInstance();
        RetroReal retroReal = retrofit.create(RetroReal.class);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<UserResponses> callDeleteReal = retroReal.deleteRealById(realEstate.getId());
                callDeleteReal.enqueue(new Callback<UserResponses>() {
                    @Override
                    public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                        if(response.isSuccessful()) {
                            UserResponses userResponses = response.body();
                            if( userResponses.getStatus() == 1) {
                                Snackbar.make(view, userResponses.getMessage(), Snackbar.LENGTH_SHORT).show();
                                realEstates.remove(i);
                            }
                            else {
                                Snackbar.make(view, userResponses.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponses> call, Throwable t) {

                    }
                });
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EditRealActivity.class);
                i.putExtra("id", realEstate.getId());
                context.startActivity(i);
            }
        });
        return view;
    }
}

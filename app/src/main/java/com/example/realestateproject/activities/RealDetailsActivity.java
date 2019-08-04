package com.example.realestateproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateproject.R;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RealDetailsActivity extends AppCompatActivity {
    private TextView tv_name, tv_address, tv_type, tv_description;

    private void initView(){
        tv_name = findViewById(R.id.tv_name_realDetail);
        tv_address = findViewById(R.id.tv_address_realDetail);
        tv_type = findViewById(R.id.tv_type_realDetail);
        tv_description = findViewById(R.id.tv_description_realDetail);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_details);
        this.initView();
        Intent i = getIntent();
        String idReal = i.getStringExtra("id");
        Retrofit retrofit = RetroClient.getInstance();
        RetroReal retroReal = retrofit.create(RetroReal.class);
        retroReal.getRealById(idReal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RealEstate>() {
                    @Override
                    public void accept(RealEstate realEstate) throws Exception {
                        tv_name.setText(realEstate.getName());
                        tv_address.setText(realEstate.getAddress());
                        tv_type.setText(realEstate.getType());
                        tv_description.setText(realEstate.getDescription());
                    }
                });
    }
}

package com.example.realestateproject.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
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

public class RealDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_name, tv_address, tv_type, tv_description, tv_area, tv_price, tv_contact;
    private Button btn_selected;
    private Toolbar toolbar3;
    private RatingBar ratingBar;


    private void initView(){
        tv_name = findViewById(R.id.tv_name_realDetail);
        tv_address = findViewById(R.id.tv_address_realDetail);
        tv_type = findViewById(R.id.tv_type_realDetail);
        tv_description = findViewById(R.id.tv_description_realDetail);
        btn_selected=findViewById(R.id.btn_selected_realDetail);
        ratingBar=findViewById(R.id.ratingBar);
        tv_area=findViewById(R.id.tv_area_realDetail);
        tv_price=findViewById(R.id.tv_price_realDetail);

        tv_contact=findViewById(R.id.tv_contact_realDetail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_details);
        this.initView();
        btn_selected.setOnClickListener(this);
        ratingBar.setRating(3);
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
                        tv_description.setText(realEstate.getDescription()+",sdjlskdhfsjdhfkjshdfkjshdjkfsdhsdkjfhskdjhfksjdhfkjsdhfkjsdhfkjsdhfjksdhfksjdhfksdjhfskjdhfskdjhfskdjhfksjdhfskjdhfjksdhfkjsdhfjskdhfkjsdhfkjsdh\nfjksdhfkjsdhfkjsdhfkjashfiuqwheihdsfkjhasdkjhasdkjahsdkjhsdjkfhsdkjfhsdkjhfsjdkhfkjsdhfkjsdhksdjhf\nfjksdhfkjsdhfkjsdhfkjashfiuqwheihdsfkjhasdkjhasdkjahsdkjhsdjkfhsdkjfhsdkjhfsjdkhfkjsdhfkjsdhksdjhf\nfjksdhfkjsdhfkjsdhfkjashfiuqwheihdsfkjhasdkjhasdkjahsdkjhsdjkfhsdkjfhsdkjhfsjdkhfkjsdhfkjsdhksdjhf");
                        tv_area.setText(realEstate.getArea().toString()+" m2");
                        tv_price.setText("$"+realEstate.getPrice().toString());
                        tv_contact.setText(realEstate.getContactNumber());
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_selected_realDetail:
                break;
    }
}}

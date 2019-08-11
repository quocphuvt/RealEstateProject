package com.example.realestateproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.realestateproject.R;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RealDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_name, tv_address, tv_type, tv_description, tv_area, tv_price, tv_contact;
    private Button btn_selected, btn_seeUsOnMap;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private ImageView iv_realImg;
    private LinearLayout layout_makeCall;
    private String phoneNumber;
    private ImageView iv_favorite;
    private boolean isLike = false;
    private String idReal;
    private String idUser;

    private void initView() {
        tv_name = findViewById(R.id.tv_name_realDetail);
        tv_address = findViewById(R.id.tv_address_realDetail);
        tv_type = findViewById(R.id.tv_type_realDetail);
        tv_description = findViewById(R.id.tv_description_realDetail);
        btn_selected = findViewById(R.id.btn_selected_realDetail);
        ratingBar = findViewById(R.id.ratingBar);
        tv_area = findViewById(R.id.tv_area_realDetail);
        tv_price = findViewById(R.id.tv_price_realDetail);
        tv_contact = findViewById(R.id.tv_contact_realDetail);
        toolbar = findViewById(R.id.toolbar_realDetail);
        btn_seeUsOnMap = findViewById(R.id.btn_seeUsOnMap);
        iv_realImg = findViewById(R.id.iv_realImg_detail);
        layout_makeCall = findViewById(R.id.layout_makeCall);
        iv_favorite = findViewById(R.id.iv_favorite);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_details);
        this.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        btn_selected.setOnClickListener(this);
        layout_makeCall.setOnClickListener(this);
        iv_favorite.setOnClickListener(this);
        ratingBar.setRating(3);
        Intent i = getIntent();
        idReal = i.getStringExtra("id");
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        idUser = sharedPreferences.getString("id", "");
        Retrofit retrofit = RetroClient.getInstance();
        RetroReal retroReal = retrofit.create(RetroReal.class);
        retroReal.getRealById(idReal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RealEstate>() {
                    @Override
                    public void accept(RealEstate realEstate) throws Exception {
                        getSupportActionBar().setTitle(realEstate.getCity());
                        tv_name.setText(realEstate.getName());
                        tv_address.setText(realEstate.getAddress());
                        tv_type.setText(realEstate.getType());
                        tv_description.setText(realEstate.getDescription() + ",sdjlskdhfsjdhfkjshdfkjshdjkfsdhsdkjfhskdjhfksjdhfkjsdhfkjsdhfkjsdhfjksdhfksjdhfksdjhfskjdhfskdjhfskdjhfksjdhfskjdhfjksdhfkjsdhfjskdhfkjsdhfkjsdh\nfjksdhfkjsdhfkjsdhfkjashfiuqwheihdsfkjhasdkjhasdkjahsdkjhsdjkfhsdkjfhsdkjhfsjdkhfkjsdhfkjsdhksdjhf\nfjksdhfkjsdhfkjsdhfkjashfiuqwheihdsfkjhasdkjhasdkjahsdkjhsdjkfhsdkjfhsdkjhfsjdkhfkjsdhfkjsdhksdjhf\nfjksdhfkjsdhfkjsdhfkjashfiuqwheihdsfkjhasdkjhasdkjahsdkjhsdjkfhsdkjfhsdkjhfsjdkhfkjsdhfkjsdhksdjhf");
                        tv_area.setText(String.format("%.0f", realEstate.getArea()) + " m2");
                        tv_price.setText("$ " + String.format("%.0f", realEstate.getPrice()));
                        tv_contact.setText(realEstate.getContactNumber());
                        iv_realImg.setImageBitmap(Utils.decodeBase64Image(realEstate.getImg()));
                        phoneNumber = realEstate.getContactNumber();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_selected_realDetail:
                break;
            case R.id.btn_seeUsOnMap:
                break;
            case R.id.layout_makeCall:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
                startActivity(intent);
                break;
            case R.id.iv_favorite:
                if (isLike) {
                    iv_favorite.setImageResource(R.drawable.ic_favorite_unactive);
                    isLike = false;
                } else {
                    iv_favorite.setImageResource(R.drawable.ic_favorite_active);
                    isLike = true;
                }
        }
    }
    //TODO: WIP
    @Override
    public void finish() {
        super.finish();
        Retrofit retrofit = RetroClient.getInstance();
        RetroUser retroUser = retrofit.create(RetroUser.class);
        retroUser.setFavoriteReal(idUser, idReal, isLike);

        Toast.makeText(this, "finish ne", Toast.LENGTH_SHORT).show();
    }
}

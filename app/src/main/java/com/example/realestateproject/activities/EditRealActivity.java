package com.example.realestateproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.realestateproject.R;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.supports.Constants;
import com.example.realestateproject.supports.LayoutInterface;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditRealActivity extends AppCompatActivity implements LayoutInterface, View.OnClickListener {
    private TextInputEditText et_realName, et_realAddress, et_realDescription, et_realPrice, et_realContact, et_realArea;
    private Button btn_realSubmit;
    private RetroReal retroReal;
    private TextView tv_cancel;
    private Spinner sp_city;
    private CheckBox chk_lease, chk_sale;
    private String city = "";
    private String img = "";
    private String location ="";
    private String type ="";
    private String idReal="";
    private Toolbar toolbar;

    private void initialView() {
        et_realName = findViewById(R.id.et_realName_editReal);
        et_realAddress = findViewById(R.id.et_realAddress_editReal);
        et_realContact = findViewById(R.id.et_realContact_editReal);
        et_realDescription = findViewById(R.id.et_realDescription_editReal);
        et_realArea = findViewById(R.id.et_realArea_editReal);
        et_realPrice = findViewById(R.id.et_realPrice_editReal);
        btn_realSubmit = findViewById(R.id.btn_realSubmit_editReal);
        tv_cancel = findViewById(R.id.tv_cancel_editReal);
        sp_city = findViewById(R.id.sp_city_editReal);
        chk_lease = findViewById(R.id.chk_lease_edit);
        chk_sale = findViewById(R.id.chk_sale_edit);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_real);
        initialView();
        Retrofit retrofitClient = RetroClient.getInstance();
        retroReal = retrofitClient.create(RetroReal.class);
        this.setToolbar("Edit your post");
        chk_sale.setOnClickListener(this);
        chk_lease.setOnClickListener(this);
        Intent i = getIntent();
        idReal = i.getStringExtra("id");
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constants.CITIES);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(cityAdapter);
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Choose city...")) {
                    //TODO: SOME THING
                    city = "";
                } else city = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_realSubmit.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        this.getRealById(idReal);
    }

    private void getRealById(String idReal) {
        Retrofit retrofit = RetroClient.getInstance();
        RetroReal retroReal = retrofit.create(RetroReal.class);
        retroReal.getRealById(idReal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RealEstate>() {
                    @Override
                    public void accept(RealEstate realEstate) throws Exception {
                        getSupportActionBar().setTitle(realEstate.getCity());
                        et_realName.setText(realEstate.getName());
                        et_realAddress.setText(realEstate.getAddress());
                        if (realEstate.getType() == "SALE") {
                            chk_sale.setChecked(true);
                        } else chk_lease.setChecked(true);
                        et_realDescription.setText(realEstate.getDescription());
                        et_realArea.setText(String.format("%.0f", realEstate.getArea()));
                        et_realPrice.setText(String.format("%.0f", realEstate.getPrice()));
                        et_realContact.setText(realEstate.getContactNumber());
                        city = realEstate.getCity();
                        img = realEstate.getImg();
                        location = realEstate.getLocation();
                        type = realEstate.getType();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_realSubmit_editReal:
                String realName = et_realName.getText().toString().trim();
                String realAddress = et_realAddress.getText().toString().trim();
                String realContact = et_realContact.getText().toString().trim();
                String realDescription = et_realDescription.getText().toString().trim();
                String realArea = et_realArea.getText().toString().trim();
                String realPrice = et_realPrice.getText().toString().trim();
                String status = Constants.STATUS[0];
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                String userId = sharedPreferences.getString("id", "");
                if (realName.isEmpty()) {
                    et_realName.setError("Field can't be empty");
                    return;
                } else if (realAddress.isEmpty()) {
                    et_realAddress.setError("Field can't be empty");
                    return;
                } else if (realContact.isEmpty()) {
                    et_realContact.setError("Field can't be empty");
                    return;
                } else if (realDescription.isEmpty()) {
                    et_realDescription.setError("Field can't be empty");
                    return;
                } else if (realPrice.isEmpty()) {
                    et_realArea.setError("Field can't be empty");
                    return;
                } else if (realArea.isEmpty()) {
                    et_realPrice.setError("Field can't be empty");
                    return;
                } else if (city.length() == 0) {
                    Toast.makeText(this, "Please choose real's city", Toast.LENGTH_SHORT).show();
                } else if (type.length() == 0) {
                    Toast.makeText(this, "Choose your type of real", Toast.LENGTH_SHORT).show();
                } else {
                    RealEstate realEstate = new RealEstate(idReal,realName, realAddress, realContact, realDescription, Double.parseDouble(realPrice), Double.parseDouble(realArea), city, type, status, location, img, userId);
                    Call<Integer> call = retroReal.updateReal(realEstate);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(EditRealActivity.this, "Real was editted!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(EditRealActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.tv_cancel_editReal:
                finish();
                break;
            case R.id.chk_lease_edit:
                type = Constants.TYPE[1];
                if (chk_sale.isChecked())
                    chk_sale.setChecked(false);
                break;
            case R.id.chk_sale_edit:
                type = Constants.TYPE[0];
                if (chk_lease.isChecked())
                    chk_lease.setChecked(false);
                break;
        }
    }

    @Override
    public void setToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
    }

}
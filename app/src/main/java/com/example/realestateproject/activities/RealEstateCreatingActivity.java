package com.example.realestateproject.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.realestateproject.R;
import com.example.realestateproject.adapters.CityAdapter;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Constants;
import com.example.realestateproject.supports.LayoutInterface;
import com.example.realestateproject.supports.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RealEstateCreatingActivity extends AppCompatActivity implements View.OnClickListener, LayoutInterface {
    private TextInputEditText et_realName, et_realAddress, et_realDescription, et_realPrice, et_realContact, et_realArea, et_location;
    private Button btn_realSubmit;
    private RetroReal retroReal;
    private ImageView iv_realImg, iv_setRealImg;
    private TextView tv_cancel;
    private Spinner sp_city;
    private CheckBox chk_lease, chk_sale;
    private String city = "";
    private String type = "";
    private Toolbar toolbar;
    private String img = "";

    private void initialView() {
        et_location = findViewById(R.id.et_realLocation_creating);
        et_realName = findViewById(R.id.et_realName_creating);
        et_realAddress = findViewById(R.id.et_realAddress_creating);
        et_realContact = findViewById(R.id.et_realContact_creating);
        et_realDescription = findViewById(R.id.et_realDescription_creating);
        et_realArea = findViewById(R.id.et_realArea_creating);
        et_realPrice = findViewById(R.id.et_realPrice_creating);
        btn_realSubmit = findViewById(R.id.btn_realSubmit_creating);
        tv_cancel = findViewById(R.id.tv_cancel_creating);
        sp_city = findViewById(R.id.sp_city_creating);
        chk_lease = findViewById(R.id.chk_lease);
        chk_sale = findViewById(R.id.chk_sale);
        toolbar = findViewById(R.id.toolbar);
        iv_realImg = findViewById(R.id.iv_realImg_creating);
        iv_setRealImg = findViewById(R.id.iv_setRealImg_creating);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_creating);
        initialView();
        Retrofit retrofitClient = RetroClient.getInstance();
        retroReal = retrofitClient.create(RetroReal.class);
        this.setToolbar("Create new post");
        iv_setRealImg.setOnClickListener(this);
        chk_lease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseRealType(view);
            }
        });
        chk_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseRealType(view);
            }
        });
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_realSubmit_creating:
                String realName = et_realName.getText().toString().trim();
                String realAddress = et_realAddress.getText().toString().trim();
                String realContact = et_realContact.getText().toString().trim();
                String realDescription = et_realDescription.getText().toString().trim();
                String realArea = et_realArea.getText().toString().trim();
                String realPrice = et_realPrice.getText().toString().trim();
                String status = Constants.STATUS[0];
                String location = et_location.getText().toString().replace(" ", "");
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
                } else if (img.length() == 0) {
                    Toast.makeText(this, "Choose your picture for your real", Toast.LENGTH_SHORT).show();
                } else {
                    RealEstate realEstate = new RealEstate(realName, realAddress, realContact, realDescription, Double.parseDouble(realPrice), Double.parseDouble(realArea), city, type, status, location, img, userId);
                    Call<UserResponses> call = retroReal.createReal(realEstate);
                    call.enqueue(new Callback<UserResponses>() {
                        @Override
                        public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                            if (response.isSuccessful()) {
                                UserResponses userResponses = response.body();
                                if( userResponses.getStatus() == 1) {
                                    Toast.makeText(RealEstateCreatingActivity.this, userResponses.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                    Log.i("Result:", response.body().toString());
                                }
                                else Toast.makeText(RealEstateCreatingActivity.this, userResponses.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponses> call, Throwable t) {
                            Toast.makeText(RealEstateCreatingActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.tv_cancel_creating:
                finish();
                break;
            case R.id.iv_setRealImg_creating:
                ImagePicker.create(this) // Activity or Fragment
                        .returnMode(ReturnMode.ALL)
                        .toolbarFolderTitle("Choose your image")
                        .toolbarImageTitle("Tap to select")
                        .single()
                        .start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            iv_realImg.setImageURI(Uri.parse(image.getPath()));
            img = Utils.encodeBase64Image(image.getPath());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void chooseRealType(View view) {
        switch (view.getId()) {
            case R.id.chk_lease:
                type = Constants.TYPE[1];
                if (chk_sale.isChecked())
                    chk_sale.setChecked(false);
                break;
            case R.id.chk_sale:
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

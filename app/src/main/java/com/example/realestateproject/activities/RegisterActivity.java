package com.example.realestateproject.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.realestateproject.R;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Constants;
import com.example.realestateproject.supports.LayoutInterface;
import com.example.realestateproject.supports.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, LayoutInterface {
    private TextInputEditText et_id, et_password, et_fullName, et_phoneNumber;
    private TextView tv_cancel_reg, tv_birthday;
    private Button btn_submit;
    private CheckBox chk_male, chk_female;
    private ImageView iv_setAvatar, iv_avatar;
    private int gender = 0;
    private Spinner sp_city;
    private Toolbar toolbar;
    private RetroUser retroUser;
    private String city= "";
    private String img = "";
    private String phoneNumberRegex = "^09+[0-9]{8}$";

    private void initialView() {
        et_id = findViewById(R.id.et_id_reg);
        et_password = findViewById(R.id.et_password_reg);
        btn_submit = findViewById(R.id.btn_submit_reg);
        tv_cancel_reg = findViewById(R.id.tv_cancel_reg);
        et_fullName = findViewById(R.id.et_fullName_reg);
        et_phoneNumber = findViewById(R.id.et_phoneNumber_reg);
        tv_birthday = findViewById(R.id.tv_birthday_reg);
        chk_male = findViewById(R.id.chk_male);
        chk_female = findViewById(R.id.chk_female);
        sp_city = findViewById(R.id.sp_city_reg);
        toolbar = findViewById(R.id.toolbar);
        iv_avatar = findViewById(R.id.iv_avatar_reg);
        iv_setAvatar = findViewById(R.id.iv_setAvatar_reg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initialView();
        this.setToolbar("Register");
        iv_setAvatar.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
        chk_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseGender(view);
            }
        });
        chk_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseGender(view);
            }
        });
        Retrofit retrofitClient = RetroClient.getInstance();
        retroUser = retrofitClient.create(RetroUser.class);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constants.CITIES);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(cityAdapter);
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Choose city...")){
                    city="";
                }
                else city = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_submit.setOnClickListener(this);
        tv_cancel_reg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_reg:
                String id = et_id.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String fullName = et_fullName.getText().toString().trim();
                String birthday = tv_birthday.getText().toString();
                String phoneNumber = et_phoneNumber.getText().toString().trim();
                if(id.length() < 3){
                    et_id.setError("ID must be more than 3 letters");
                } else if(password.length() < 3) {
                    et_password.setError("Password must be more than 3 letters");
                } else if(phoneNumber.length() == 0 || !phoneNumber.matches(phoneNumberRegex)) {
                    et_phoneNumber.setError("Invalid phone number");
                } else if(img.length() == 0) {
                    Toast.makeText(this, "Please set your avatar", Toast.LENGTH_SHORT).show();
                } else {
                    UserModel userModel = new UserModel(id, password, fullName, birthday, city, phoneNumber, gender, img);
                    this.registerUser(userModel, view);
                    Snackbar.make(view, "User was created!", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_cancel_reg:
                //Doing something
                finish();
                break;
            case R.id.tv_birthday_reg:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
                            Date d = simpleDate.parse(date);
                            tv_birthday.setText(simpleDate.format(d));
                        } catch (Exception e) {

                        }

                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.iv_setAvatar_reg:
                ImagePicker.create(this) // Activity or Fragment
                        .returnMode(ReturnMode.ALL)
                        .toolbarFolderTitle("Choose your image")
                        .toolbarImageTitle("Tap to select")
                        .single()
                        .start();
                break;
        }
    }

    private void registerUser(UserModel userModel, View view) {
        if (TextUtils.isEmpty(userModel.getId()) || TextUtils.isEmpty(userModel.getPassword())) {
            Toast.makeText(this, "Id/Password is required", Toast.LENGTH_SHORT).show();
        } else {
            Call<UserResponses> call = retroUser.registerUser(userModel);
            call.enqueue(new Callback<UserResponses>() {
                @Override
                public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getStatus() == 0) {
                            Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                        else if (response.body().getStatus() == 1) {
                            Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponses> call, Throwable t) {

                }
            });
        }
    }

    private void chooseGender(View view) {
        switch (view.getId()) {
            case R.id.chk_male:
                gender = 0;
                if (chk_female.isChecked())
                    chk_female.setChecked(false);
                break;
            case R.id.chk_female:
                gender = 1;
                if (chk_male.isChecked())
                    chk_male.setChecked(false);
                break;
        }
    }

    @Override
    public void setToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            iv_avatar.setImageURI(Uri.parse(image.getPath()));
            img = Utils.encodeBase64Image(image.getPath());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.MainActivity;
import com.example.realestateproject.R;
import com.example.realestateproject.adapters.CityAdapter;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Constants;
import com.example.realestateproject.supports.LayoutInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, LayoutInterface {
    private TextInputEditText et_id, et_password, et_fullName, et_phoneNumber;
    private TextView tv_cancel_reg, tv_birthday;
    private Button btn_submit;
    private CheckBox chk_male, chk_female;
    private int gender = 0;
    private Spinner sp_city;
    private Toolbar toolbar;
    private RetroUser retroUser;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initialView();
        this.setToolbar("Register");

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
        //Init retrofit to class
        Retrofit retrofitClient = RetroClient.getInstance();
        retroUser = retrofitClient.create(RetroUser.class);
        CityAdapter cityAdapter = new CityAdapter(Constants.CITIES, this);
        //TODO: GET CITY
        sp_city.setAdapter(cityAdapter);
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
                String city = "Ho Chi Minh";
                String phoneNumber = et_phoneNumber.getText().toString().trim();
                UserModel userModel = new UserModel(id, password, fullName, birthday, city, phoneNumber, gender);
                this.registerUser(userModel);
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
        }
    }

    private void registerUser(UserModel userModel) {
        if (TextUtils.isEmpty(userModel.getId()) || TextUtils.isEmpty(userModel.getPassword())) {
            Toast.makeText(this, "Id/Password is required", Toast.LENGTH_SHORT).show();
        } else {
            Call<UserModel> call = retroUser.registerUser(userModel);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        if(response.body().getId() != null){
                            Toast.makeText(RegisterActivity.this, "User was created!", Toast.LENGTH_SHORT).show();
                            finish();
                            Log.i("Result:", response.body().toString());
                        }
                        else Toast.makeText(RegisterActivity.this, "ID was existed", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
}

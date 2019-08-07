package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.adapters.CityAdapter;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Constants;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateProfileUserActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText  et_, et_fullName, et_phoneNumber;
    private CheckBox chk_male, chk_female;
    private TextView tv_cancel, tv_birthday;
    private Button btn_update;
    private Spinner sp_city;
    private int gender = 0;
    private Toolbar toolbar;
    private RetroUser retroUser;
    private String city;

    private void initialView() {
        btn_update = findViewById(R.id.btn_submit_editUser);
        tv_cancel = findViewById(R.id.tv_cancel_editUser);
        et_fullName = findViewById(R.id.et_fullName_editUser);
        et_phoneNumber = findViewById(R.id.et_phoneNumber_editUser);
        tv_birthday = findViewById(R.id.tv_birthday_editUser);
        chk_male = findViewById(R.id.chk_male_editUser);
        chk_female = findViewById(R.id.chk_female_editUser);
        sp_city = findViewById(R.id.sp_city_editUser);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_user);
        this.initialView();
        this.setToolbar("My Profile");
        tv_birthday.setOnClickListener(this);
        Retrofit retrofilClient = RetroClient.getInstance();
        retroUser = retrofilClient.create(RetroUser.class);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constants.CITIES);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(cityAdapter);
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Choose city...")){
                    //TODO: SOME THING
                    city="";
                }
                else city = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_update.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_editUser:
                String fullname = et_fullName.getText().toString();
                String birthday = tv_birthday.getText().toString();
                String phonenumber = et_phoneNumber.getText().toString().trim();
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                String userId = sharedPreferences.getString("id", ""); //Get user id when logining successful.
                UserModel userModel = new UserModel(userId, fullname, birthday, phonenumber, city, gender);
                Call<UserModel> call = retroUser.updateUserData(userModel);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(UpdateProfileUserActivity.this, "Your profile was changed", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                    }
                });
                break;


            case R.id.tv_cancel_editUser:
                //Doing something
                finish();
                break;
            case R.id.tv_birthday_editUser:
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
    private void chooseGender(View view) {
        switch (view.getId()) {
            case R.id.chk_male_editUser:
                gender = 0;
                if (chk_female.isChecked())
                    chk_female.setChecked(false);
                break;
            case R.id.chk_female_editUser:
                gender = 1;
                if (chk_male.isChecked())
                    chk_male.setChecked(false);
                break;
        }
    }


    public void setToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);

    }
}
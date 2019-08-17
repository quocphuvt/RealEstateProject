package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.realestateproject.R;
import com.example.realestateproject.adapters.CityAdapter;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Constants;
=======
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.realestateproject.R;
import com.example.realestateproject.adapters.CityAdapter;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Constants;
import com.example.realestateproject.supports.Utils;
import com.google.android.material.snackbar.Snackbar;
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

<<<<<<< HEAD
=======
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateProfileUserActivity extends AppCompatActivity implements View.OnClickListener {
<<<<<<< HEAD
    private TextInputEditText  et_, et_fullName, et_phoneNumber;
=======
    private TextInputEditText et_fullName, et_phoneNumber;
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
    private CheckBox chk_male, chk_female;
    private TextView tv_cancel, tv_birthday;
    private Button btn_update;
    private Spinner sp_city;
    private int gender = 0;
    private Toolbar toolbar;
    private RetroUser retroUser;
<<<<<<< HEAD
=======
    private String city;
    private ImageView iv_avatar, iv_setAvatar;
    private String img;
    private String userId;
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b

    private void initialView() {
        btn_update = findViewById(R.id.btn_submit_editUser);
        tv_cancel = findViewById(R.id.tv_cancel_editUser);
        et_fullName = findViewById(R.id.et_fullName_editUser);
        et_phoneNumber = findViewById(R.id.et_phoneNumber_editUser);
        tv_birthday = findViewById(R.id.tv_birthday_editUser);
<<<<<<< HEAD
        chk_male = findViewById(R.id.chk_male);
        chk_female = findViewById(R.id.chk_female);
        sp_city = findViewById(R.id.sp_city_editUser);
        toolbar = findViewById(R.id.toolbar);
=======
        chk_male = findViewById(R.id.chk_male_editUser);
        chk_female = findViewById(R.id.chk_female_editUser);
        sp_city = findViewById(R.id.sp_city_editUser);
        toolbar = findViewById(R.id.toolbar);
        iv_avatar = findViewById(R.id.iv_avatar_edit);
        iv_setAvatar = findViewById(R.id.iv_setAvatar_edit);
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_user);
        this.initialView();
<<<<<<< HEAD
        this.setToolbar("UpdateProfile");

        tv_birthday.setOnClickListener(this);

        Retrofit retrofilClient = RetroClient.getInstance();
        retroUser = retrofilClient.create(RetroUser.class);
        CityAdapter cityAdapter = new CityAdapter(Constants.CITIES, this);
        //TODO: GET CITY
        sp_city.setAdapter(cityAdapter);
=======
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
                    city="";
                }
                else city = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
        getCurrentUser(userId);
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
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
        btn_update.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

<<<<<<< HEAD

=======
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_editUser:
<<<<<<< HEAD

                String fullname = et_fullName.getText().toString();
                String birthday = tv_birthday.getText().toString();
                String phonenumber = et_phoneNumber.getText().toString().trim();
                String city = "Ho Chi Minh";
                SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                String id = preferences.getString("id", "");
                UserModel userModel = new UserModel(id, fullname, birthday, phonenumber, city, gender);
                Call<UserModel> call = retroUser.updateUser(userModel);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {

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

=======
                String fullname = et_fullName.getText().toString().trim();
                String birthday = tv_birthday.getText().toString().trim();
                String phonenumber = et_phoneNumber.getText().toString().trim();
                if(fullname.isEmpty() || phonenumber.isEmpty() || city.length() == 0){
                    Snackbar.make(view, "Please type all field", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    UserModel userModel = new UserModel(userId, fullname, birthday, phonenumber, city, gender, img);
                    Call<UserResponses> call = retroUser.updateUserData(userModel);
                    call.enqueue(new Callback<UserResponses>() {
                        @Override
                        public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                            if(response.isSuccessful()){
                                UserResponses userResponses = response.body();
                                if(userResponses.getStatus() == 1) {
                                    Snackbar.make(view, userResponses.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    finish();
                                }
                                else if(userResponses.getStatus() == 0) {
                                    Snackbar.make(view, userResponses.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponses> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.tv_cancel_editUser:
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
            case R.id.iv_setAvatar_edit:
                ImagePicker.create(this) // Activity or Fragment
                        .returnMode(ReturnMode.ALL)
                        .toolbarFolderTitle("Choose your image")
                        .toolbarImageTitle("Tap to select")
                        .single()
                        .start();
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

    private void getCurrentUser(String id) {
        Call<UserResponses> callCurrentUser = retroUser.getCurrentUser(id);
        callCurrentUser.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if(response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if(userResponses.getStatus() == 1) {
                        UserModel userModel = userResponses.getUserModel();
                        et_fullName.setText(userModel.getFullName());
                        et_phoneNumber.setText(userModel.getPhoneNumber());
                        iv_avatar.setImageBitmap(Utils.decodeBase64Image(userModel.getAvatar()));
                        tv_birthday.setText(userModel.getBirthday());
                        city = userModel.getCity();
                        if(userModel.getGender() == 0) {
                            chk_male.setChecked(true);
                            return;
                        } else {
                            chk_female.setChecked(true);
                            return;
                        }
                    }
                    else {
                        Toast.makeText(UpdateProfileUserActivity.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b

    public void setToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);

    }
<<<<<<< HEAD
}
=======
}
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b

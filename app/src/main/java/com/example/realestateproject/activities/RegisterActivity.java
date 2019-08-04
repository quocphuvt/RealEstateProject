package com.example.realestateproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;

import org.json.JSONString;

import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Pattern PASSWORD_PARTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
//                    "(?=.*[a-zA-Z])" +      //any letter
//                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private EditText et_id, et_password, et_fullName, et_address, et_phoneNumber;
    private TextView tv_birthday;
    private Button btn_submit, btn_cancle, btn_pickBirthday;
    private CheckBox chk_male, chk_female;
    private int gender = -1;
    public String regexStr = "^[+]?[0-9]{8,15}$";
    private RetroUser retroUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void initialView() {
        et_id = findViewById(R.id.et_id_reg);
        et_password = findViewById(R.id.et_password_reg);
        btn_submit = findViewById(R.id.btn_submit_reg);
        btn_cancle = findViewById(R.id.btn_cancle_reg);
        et_fullName = findViewById(R.id.et_fullName_reg);
        et_address = findViewById(R.id.et_address_reg);
        et_phoneNumber = findViewById(R.id.et_phoneNumber_reg);
        tv_birthday = findViewById(R.id.tv_birthday_reg);
        btn_pickBirthday = findViewById(R.id.btn_birthday_reg);
        chk_male = findViewById(R.id.chk_male);
        chk_female = findViewById(R.id.chk_female);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initialView();
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
        btn_submit.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_reg:
                String id = et_id.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String fullName = et_fullName.getText().toString().trim();
                String birthday = tv_birthday.getText().toString();
                String address = et_address.getText().toString().trim();
                String phoneNumber = et_phoneNumber.getText().toString().trim();
                UserModel userModel = new UserModel(id, password, fullName, birthday, address, phoneNumber, gender);
                this.registerUser(userModel);
                break;
            case R.id.btn_cancle_reg:
                //Doing something
                finish();
                break;
        }
    }

    private void registerUser(UserModel userModel) {

        if (TextUtils.isEmpty(userModel.getId()) || TextUtils.isEmpty(userModel.getPassword())) {
            Toast.makeText(this, "Id/Password is required", Toast.LENGTH_SHORT).show();
        } else if (userModel.getId().length() < 6) {
            Toast.makeText(this, "Id/Id must at least 6 letters", Toast.LENGTH_SHORT).show();
        } else if (!PASSWORD_PARTERN.matcher(userModel.getPassword()).matches()) {
            Toast.makeText(this, "Password must include at least one number, be at least 6 characters long, upper case", Toast.LENGTH_SHORT).show();
        } else if (userModel.getPhoneNumber().length() < 10 || userModel.getPhoneNumber().length() > 13 || userModel.getPhoneNumber().matches(regexStr) == false) {
            Toast.makeText(this, "Please enter" + "\n" + " vaild phone number  ", Toast.LENGTH_SHORT).show();
        }
        else if(gender == -1){
            Toast.makeText(this, "Gender is not empty", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "OK NHA", Toast.LENGTH_SHORT).show();
            Call<UserModel> call = retroUser.registerUser(userModel);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Result:", response.body().toString());

                    }
                }


                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {

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
}

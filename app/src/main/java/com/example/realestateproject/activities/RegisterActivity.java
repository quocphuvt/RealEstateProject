package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText input_edt_ID, input_edt_Password, input_edt_Name, input_edt_Birthday, input_edt_Phone;
    Spinner sp_City;
    Button btn_Register, btn_Cancel;
    RadioGroup rdG_Gender;
    CheckBox chkMale, chkFemale;
    UserModel userModel;
    private int gender = 0;
    private RetroUser retroUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void initialView() {

        input_edt_ID = findViewById(R.id.input_edt_ID);
        input_edt_Password = findViewById(R.id.input_edt_Password);
        input_edt_Name = findViewById(R.id.input_edt_Name);
        sp_City = findViewById(R.id.spinner_city);
        input_edt_Birthday = findViewById(R.id.input_edt_Birthday);
        input_edt_Phone = findViewById(R.id.input_edt_Phone);
        rdG_Gender = findViewById(R.id.rdG_Gender);
        chkFemale=findViewById(R.id.chkFemale);
        chkMale=findViewById(R.id.chkMale);
        btn_Register = findViewById(R.id.btnRegister);
        btn_Cancel = findViewById(R.id.btnCancel);

        String[] ds = {"Ho Chi Minh", "Ha Noi", "Can Tho", "Da Nang", "Nha Trang","Vung Tau"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,ds);
        sp_City.setAdapter(adapter);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initialView();
        chkMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseGender(v);
            }
        });
        chkFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseGender(v);
            }
        });
        //Init retrofit to class
        Retrofit retrofitClient = RetroClient.getInstance();
        retroUser = retrofitClient.create(RetroUser.class);
        btn_Register.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                String id = input_edt_ID.getText().toString().trim();
                String password = input_edt_Password.getText().toString().trim();
                String fullName = input_edt_Name.getText().toString().trim();
                String birthday = input_edt_Birthday.getText().toString();
                String address= sp_City.getSelectedItem().toString().trim();
                String phoneNumber = input_edt_Phone.getText().toString().trim();
                UserModel userModel = new UserModel(id, password, fullName, birthday, address,phoneNumber, gender);
                this.registerUser(userModel);
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
                    if(response.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Result:", response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "fall", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void chooseGender(View view) {
        switch (view.getId()){
            case R.id.chkMale:
                gender = 0;
                if(chkFemale.isChecked())
                    chkFemale.setChecked(false);
                break;
            case R.id.chkFemale:
                gender = 1;
                if(chkMale.isChecked())
                    chkMale.setChecked(false);
                break;
        }
    }

}

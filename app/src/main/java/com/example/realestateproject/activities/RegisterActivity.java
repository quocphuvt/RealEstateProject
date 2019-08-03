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

    private void initialView() {

        input_edt_ID = findViewById(R.id.input_edt_ID);
        input_edt_Password = findViewById(R.id.input_edt_Password);
        input_edt_Name = findViewById(R.id.input_edt_Name);
        sp_City = findViewById(R.id.spinner_city);
        input_edt_Birthday = findViewById(R.id.input_edt_Birthday);
        input_edt_Phone = findViewById(R.id.input_edt_Phone);
        rdG_Gender = findViewById(R.id.rdG_Gender);

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


    }

    @Override
    public void onClick(View view) {

    }

    private void registerUser(UserModel userModel) {

    }

    private void chooseGender(View view) {

    }
}

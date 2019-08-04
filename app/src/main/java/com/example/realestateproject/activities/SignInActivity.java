package com.example.realestateproject.activities;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.LayoutInterface;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, LayoutInterface {
    private EditText et_id, et_pasword;
    private Button btn_logIn;
    private Toolbar toolbar;
    private TextView tv_register;
    private RetroUser retroUser;

    private void inititalView() {
        et_id = findViewById(R.id.et_id_signIn);
        et_pasword = findViewById(R.id.et_password_signIn);
        btn_logIn = findViewById(R.id.btn_login_signIn);
        tv_register = findViewById(R.id.btn_register_signIn);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.inititalView();
        this.setToolbar("Sign In");
        Retrofit retrofitClient = RetroClient.getInstance();
        retroUser = retrofitClient.create(RetroUser.class);

        btn_logIn.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_signIn:
                final String id = et_id.getText().toString().trim();
                String password = et_pasword.getText().toString().trim();
                retroUser.checkUserLogin(id, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<UserResponses>() {
                            @Override
                            public void accept(UserResponses userResponses) throws Exception {
                                if (userResponses.getStatus().equals("id failed")) {
                                    Toast.makeText(SignInActivity.this, "" + userResponses.getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (userResponses.getStatus().equals("password failed")) {
                                    Toast.makeText(SignInActivity.this, "" + userResponses.getMessage(), Toast.LENGTH_SHORT).show();
                                } else if(userResponses.getStatus().equals("success")){
                                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("id", id);
                                    editor.apply();
                                    Toast.makeText(SignInActivity.this, "" + userResponses.getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                }
                            }
                        });
                break;
            case R.id.btn_register_signIn:
                startActivity(new Intent(this, RegisterActivity.class));
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

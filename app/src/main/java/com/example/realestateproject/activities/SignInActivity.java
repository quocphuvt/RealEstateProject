package com.example.realestateproject.activities;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_id, et_pasword;
    private Button btn_logIn;
    private Toolbar toolbar;
    private TextView tv_register;
    private RetroUser retroUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        Retrofit retrofitClient = RetroClient.getInstance();
        retroUser = retrofitClient.create(RetroUser.class);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sign In");
        toolbar.setTitleTextColor(Color.WHITE);

        btn_logIn.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_signIn:
                String id = et_id.getText().toString().trim();
                String password = et_pasword.getText().toString().trim();
                Call<String> call = retroUser.checkUserLogin(id, password);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SignInActivity.this, ""+response.message().toString(), Toast.LENGTH_SHORT).show();
                            Log.d("login", response.message().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_register_signIn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
